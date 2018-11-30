/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

 /* 
 * Modifications made by waalbrugwire team  
 * for interface purposes
 * marked through the code 
 * feb - 2018 / ? - 2018
 */
package org.optaplanner.examples.nurserostering.swingui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* 
 * added imports for adding GUI components
 * waalbrugwire.nl, 9-2-2018  
 */
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.*;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import javax.print.PrintService;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.lang3.tuple.Pair;

import org.optaplanner.examples.common.business.SolutionBusiness;
import org.optaplanner.examples.nurserostering.domain.ShiftType;
import org.optaplanner.examples.nurserostering.domain.contract.Contract;
import org.optaplanner.examples.nurserostering.persistence.NurseRosteringImporter;
//import org.optaplanner.examples.nurserostering.persistence.NurseRosteringInputBuilder;

/* 
 * end of extra imports for adding GUI components
 * waalbrugwire.nl, 9-2-2018  
 */
import org.optaplanner.examples.common.swingui.SolutionPanel;
import org.optaplanner.examples.nurserostering.domain.Employee;
import org.optaplanner.examples.nurserostering.domain.NurseRoster;
import org.optaplanner.examples.nurserostering.domain.NurseRosterParametrization;
import org.optaplanner.examples.nurserostering.domain.Shift;
import org.optaplanner.examples.nurserostering.domain.ShiftAssignment;
import org.optaplanner.examples.nurserostering.domain.ShiftDate;

public class NurseRosteringPanel extends SolutionPanel<NurseRoster>
{
    public static final String LOGO_PATH = "/org/optaplanner/examples/nurserostering/swingui/nurseRosteringLogo.png";
    
    private final ImageIcon employeeIcon;
    private final ImageIcon deleteEmployeeIcon;
    
    private JPanel employeeListPanel;
    
    private JTextField planningWindowStartField;
    private AbstractAction advancePlanningWindowStartAction;
    //added AbstractAction, waalbrugwire, 11-2-2018
    private AbstractAction invokeEditDialogBoxAction;
    //added AbstractAction, waalbrugwire, 4-3-2018
    private AbstractAction onCloseEditDialogBoxAction;
    //added AbstractAction, waalbrugwire, 13-5-2018
    private AbstractAction startPrint;
    
    private EmployeePanel unassignedPanel;
    private Map<Employee, EmployeePanel> employeeToPanelMap;

    //added custom Panels, waalbrugwire, 5-3-2018
    private CustomTabMinMaxContractLinesPanel tabMinMaxContractLinePanel;
    private CustomTabContractLinesPanel tabContractLinePanel;
    private CustomTabPatternLinesPanel tabPatternLinePanel;
    private CustomTabDatePanel tabDatePanel;
    private CustomTabShiftTypePanel tabShiftTypePanel;
    private CustomTabEmployeePanel tabEmployeePanel;
    //end of added custom Panels

    //added Maps from NurseRosteringImporter, waalbrugwire 2018-2-19
    protected Map<LocalDate, ShiftDate> shiftDateMap;
    protected Map<String, ShiftType> shiftTypeMap;
    protected Map<Pair<LocalDate, String>, Shift> dateAndShiftTypeToShiftMap;
    protected Map<Pair<DayOfWeek, ShiftType>, List<Shift>> dayOfWeekAndShiftTypeToShiftListMap;
    //end of added mappings

    public NurseRosteringPanel()
    {
        /* employeeIcon changed by waalbrugwire, 9 - 2 - 2018 */
        // employeeIcon = new ImageIcon(getClass().getResource("employee.png"));
        //to
        employeeIcon = new ImageIcon(getClass().getResource("syringe-32.png"));
        
        deleteEmployeeIcon = new ImageIcon(getClass().getResource("deleteEmployee.png"));
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        // creating the menu 
        // waalbrugwire, 9-2-2018
        // JMenuBar menuBar = createMenuBar();
        // no longer using menu but edit dialog box, waalbrugwire, 28-2-2018

        createEmployeeListPanel();
        JPanel headerPanel = createHeaderPanel();
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(headerPanel).addComponent(employeeListPanel));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(headerPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                .addComponent(employeeListPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.PREFERRED_SIZE));
    }
    
    public ImageIcon getEmployeeIcon()
    {
        return employeeIcon;
    }
    
    public ImageIcon getDeleteEmployeeIcon()
    {
        return deleteEmployeeIcon;
    }
    
    private JPanel createHeaderPanel()
    {
        JPanel headerPanel = new JPanel(new BorderLayout(20, 0));
        JPanel planningWindowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        planningWindowPanel.add(new JLabel("Planning window start:"));
        planningWindowStartField = new JTextField(10);
        planningWindowStartField.setEditable(false);
        planningWindowPanel.add(planningWindowStartField);
        // shortened text "Advance 1 day into the future" to "Advance 1 day" , WaalbrugWire, 11-2-2018
        advancePlanningWindowStartAction = new AbstractAction("Advance 1 day")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                advancePlanningWindowStart();
            }
        };
        //added handler for the invokeEditDialogBox, WaalbrugWire, 11-2-2018
        invokeEditDialogBoxAction = new AbstractAction("Edit")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                invokeEditDialogBox();
            }
        };
        //added handler for the startPrint, waalbrugwire, 13-5-2018
        startPrint = new AbstractAction("Print")
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                startPrint();
            }
        };
        
        advancePlanningWindowStartAction.setEnabled(false);
        planningWindowPanel.add(new JButton(advancePlanningWindowStartAction));
        //added button to invoke EditDialogBox
        planningWindowPanel.add(new JButton(invokeEditDialogBoxAction));
        //added button to start printing
        planningWindowPanel.add(new JButton(startPrint));
        
        headerPanel.add(planningWindowPanel, BorderLayout.WEST);
        //changed explanation
        //from
        //JLabel shiftTypeExplanation = new JLabel("E = Early shift, L = Late shift, ...");
        //to
        JLabel shiftTypeExplanation = new JLabel("1 = 8hr, 2 = 24hr, wk = Weekend");
        headerPanel.add(shiftTypeExplanation, BorderLayout.CENTER);
        return headerPanel;
    }
    
    private void createEmployeeListPanel()
    {
        employeeListPanel = new JPanel();
        employeeListPanel.setLayout(new BoxLayout(employeeListPanel, BoxLayout.Y_AXIS));
        unassignedPanel = new EmployeePanel(this, Collections.<ShiftDate>emptyList(), Collections.<Shift>emptyList(),
                null);
        employeeListPanel.add(unassignedPanel);
        employeeToPanelMap = new LinkedHashMap<>();
        employeeToPanelMap.put(null, unassignedPanel);
    }
    
    @Override
    public void resetPanel(NurseRoster nurseRoster)
    {
        for (EmployeePanel employeePanel : employeeToPanelMap.values())
        {
            if (employeePanel.getEmployee() != null)
            {
                employeeListPanel.remove(employeePanel);
            }
        }
        employeeToPanelMap.clear();
        employeeToPanelMap.put(null, unassignedPanel);
        unassignedPanel.clearShiftAssignments();
        preparePlanningEntityColors(nurseRoster.getShiftAssignmentList());
        List<ShiftDate> shiftDateList = nurseRoster.getShiftDateList();
        List<Shift> shiftList = nurseRoster.getShiftList();
        unassignedPanel.setShiftDateListAndShiftList(shiftDateList, shiftList);
        updatePanel(nurseRoster);
        advancePlanningWindowStartAction.setEnabled(true);
        planningWindowStartField.setText(nurseRoster.getNurseRosterParametrization().getPlanningWindowStart().getLabel());
    }

    //5-5-2018 WaalbrugWire on updatePanel
    //Q1 : can we /do we need to add code here for added employee?
    //Q2 : can we /do we need to do a resetShiftListPanel(); here to display dayOFF/On requests?
    //End comments
    @Override
    public void updatePanel(NurseRoster nurseRoster)
    {
        preparePlanningEntityColors(nurseRoster.getShiftAssignmentList());
        List<ShiftDate> shiftDateList = nurseRoster.getShiftDateList();

        //debug statement  WaalbrugWire, 7-5-2018
        System.out.println("First ShiftDate has hashcode : " + shiftDateList.get(0).hashCode());
        
        List<Shift> shiftList = nurseRoster.getShiftList();
        Set<Employee> deadEmployeeSet = new LinkedHashSet<>(employeeToPanelMap.keySet());
        deadEmployeeSet.remove(null);
        for (Employee employee : nurseRoster.getEmployeeList())
        {
            deadEmployeeSet.remove(employee);
            EmployeePanel employeePanel = employeeToPanelMap.get(employee);
            if (employeePanel == null)
            {
                employeePanel = new EmployeePanel(this, shiftDateList, shiftList, employee);
                employeeListPanel.add(employeePanel);
                employeeToPanelMap.put(employee, employeePanel);
            }
            employeePanel.clearShiftAssignments();
        }
        unassignedPanel.clearShiftAssignments();
        
        for (ShiftAssignment shiftAssignment : nurseRoster.getShiftAssignmentList())
        {
            Employee employee = shiftAssignment.getEmployee();
            EmployeePanel employeePanel = employeeToPanelMap.get(employee);
            employeePanel.addShiftAssignment(shiftAssignment);
        }
        for (Employee deadEmployee : deadEmployeeSet)
        {
            EmployeePanel deadEmployeePanel = employeeToPanelMap.remove(deadEmployee);
            employeeListPanel.remove(deadEmployeePanel);
        }
        for (EmployeePanel employeePanel : employeeToPanelMap.values())
        {
            employeePanel.update();
        }
    }
    
    @Override
    public boolean isIndictmentHeatMapEnabled()
    {
        return true;
    }
    
    private void advancePlanningWindowStart()
    {
        logger.info("Advancing planningWindowStart.");
        if (solutionBusiness.isSolving())
        {
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
                    "The GUI does not support this action yet during solving.\nOptaPlanner itself does support it.\n"
                    + "\nTerminate solving first and try again.",
                    "Unsupported in GUI", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        doProblemFactChange(scoreDirector ->
        {
            NurseRoster nurseRoster = scoreDirector.getWorkingSolution();
            NurseRosterParametrization nurseRosterParametrization = nurseRoster.getNurseRosterParametrization();
            List<ShiftDate> shiftDateList = nurseRoster.getShiftDateList();
            ShiftDate planningWindowStart = nurseRosterParametrization.getPlanningWindowStart();
            int windowStartIndex = shiftDateList.indexOf(planningWindowStart);
            if (windowStartIndex < 0)
            {
                throw new IllegalStateException("The planningWindowStart ("
                        + planningWindowStart + ") must be in the shiftDateList ("
                        + shiftDateList + ").");
            }
            ShiftDate oldLastShiftDate = shiftDateList.get(shiftDateList.size() - 1);
            ShiftDate newShiftDate = new ShiftDate();
            newShiftDate.setId(oldLastShiftDate.getId() + 1L);
            newShiftDate.setDayIndex(oldLastShiftDate.getDayIndex() + 1);
            newShiftDate.setDate(oldLastShiftDate.getDate().plusDays(1));
            List<Shift> refShiftList = planningWindowStart.getShiftList();
            List<Shift> newShiftList = new ArrayList<>(refShiftList.size());
            newShiftDate.setShiftList(newShiftList);
            nurseRoster.getShiftDateList().add(newShiftDate);
            scoreDirector.afterProblemFactAdded(newShiftDate);
            Shift oldLastShift = nurseRoster.getShiftList().get(nurseRoster.getShiftList().size() - 1);
            long shiftId = oldLastShift.getId() + 1L;
            int shiftIndex = oldLastShift.getIndex() + 1;
            long shiftAssignmentId = nurseRoster.getShiftAssignmentList().get(
                    nurseRoster.getShiftAssignmentList().size() - 1).getId() + 1L;
            for (Shift refShift : refShiftList)
            {
                Shift newShift = new Shift();
                newShift.setId(shiftId);
                shiftId++;
                newShift.setShiftDate(newShiftDate);
                newShift.setShiftType(refShift.getShiftType());
                newShift.setIndex(shiftIndex);
                shiftIndex++;
                newShift.setRequiredEmployeeSize(refShift.getRequiredEmployeeSize());
                newShiftList.add(newShift);
                nurseRoster.getShiftList().add(newShift);
                scoreDirector.afterProblemFactAdded(newShift);
                for (int indexInShift = 0; indexInShift < newShift.getRequiredEmployeeSize(); indexInShift++)
                {
                    ShiftAssignment newShiftAssignment = new ShiftAssignment();
                    newShiftAssignment.setId(shiftAssignmentId);
                    shiftAssignmentId++;
                    newShiftAssignment.setShift(newShift);
                    newShiftAssignment.setIndexInShift(indexInShift);
                    nurseRoster.getShiftAssignmentList().add(newShiftAssignment);
                    scoreDirector.afterEntityAdded(newShiftAssignment);
                }
            }
            windowStartIndex++;
            ShiftDate newPlanningWindowStart = shiftDateList.get(windowStartIndex);
            scoreDirector.beforeProblemPropertyChanged(nurseRosterParametrization);
            nurseRosterParametrization.setPlanningWindowStart(newPlanningWindowStart);
            nurseRosterParametrization.setLastShiftDate(newShiftDate);
            scoreDirector.afterProblemPropertyChanged(nurseRosterParametrization);
        }, true);
    }
    
    public void deleteEmployee(final Employee employee)
    {
        logger.info("Scheduling delete of employee ({}).", employee);
        doProblemFactChange(scoreDirector ->
        {
            NurseRoster nurseRoster = scoreDirector.getWorkingSolution();
            Employee workingEmployee = scoreDirector.lookUpWorkingObject(employee);
            if (workingEmployee == null)
            {
                // The employee has already been deleted (the UI asked to changed the same employee twice), so do nothing
                return;
            }
            // First remove the problem fact from all planning entities that use it
            for (ShiftAssignment shiftAssignment : nurseRoster.getShiftAssignmentList())
            {
                if (shiftAssignment.getEmployee() == workingEmployee)
                {
                    scoreDirector.beforeVariableChanged(shiftAssignment, "employee");
                    shiftAssignment.setEmployee(null);
                    scoreDirector.afterVariableChanged(shiftAssignment, "employee");
                }
            }
            // A SolutionCloner does not clone problem fact lists (such as employeeList)
            // Shallow clone the employeeList so only workingSolution is affected, not bestSolution or guiSolution
            ArrayList<Employee> employeeList = new ArrayList<>(nurseRoster.getEmployeeList());
            nurseRoster.setEmployeeList(employeeList);
            // Remove it the problem fact itself
            scoreDirector.beforeProblemFactRemoved(workingEmployee);
            employeeList.remove(workingEmployee);
            scoreDirector.afterProblemFactRemoved(workingEmployee);
            scoreDirector.triggerVariableListeners();
        });
    }
    
    public void moveShiftAssignmentToEmployee(ShiftAssignment shiftAssignment, Employee toEmployee)
    {
        solutionBusiness.doChangeMove(shiftAssignment, "employee", toEmployee);
        solverAndPersistenceFrame.resetScreen();
    }

    /* 
     *
     * below is the added functionality to add a EditDialogBox and more
     * waalbrugwire, 9-2-2018
     *
     */
    public void editEmployee(final Employee employee, String code, String name, Contract contract)
    {
        logger.info("Scheduling delete of employee ({}).", employee);
        doProblemFactChange(scoreDirector ->
        {
            NurseRoster nurseRoster = scoreDirector.getWorkingSolution();
            Employee workingEmployee = scoreDirector.lookUpWorkingObject(employee);
            if (workingEmployee == null)
            {
                // The employee does not exist, so do nothing
                return;
            }
            // First remove the problem fact from all planning entities that use it
            for (ShiftAssignment shiftAssignment : nurseRoster.getShiftAssignmentList())
            {
                if (shiftAssignment.getEmployee() == workingEmployee)
                {
                    scoreDirector.beforeVariableChanged(shiftAssignment, "employee");
                    shiftAssignment.getEmployee().setCode(code);
                    shiftAssignment.getEmployee().setName(name);
                    shiftAssignment.getEmployee().setContract(contract);
                    scoreDirector.afterVariableChanged(shiftAssignment, "employee");
                }
            }
            // A SolutionCloner does not clone problem fact lists (such as employeeList)
            // Shallow clone the employeeList so only workingSolution is affected, not bestSolution or guiSolution
            ArrayList<Employee> employeeList = new ArrayList<>(nurseRoster.getEmployeeList());
            nurseRoster.setEmployeeList(employeeList);
            // Remove it the problem fact itself
            scoreDirector.beforeVariableChanged(workingEmployee, name);
            //employeeList.remove(workingEmployee);
            workingEmployee.setName(name);
            scoreDirector.afterVariableChanged(workingEmployee, name);
            scoreDirector.triggerVariableListeners();
        });
    }
    
    private void invokeEditDialogBox()
    {
        // -> werkt niet -> NurseRoster nurseRoster = scoreDirector.getWorkingSolution();
        //NurseRosterParametrization nurseRosterParametrization = nurseRoster.getNurseRosterParametrization();
        //NurseRoster nurseRoster = scoreDirector.getWorkingSolution();
        //NurseRoster nurseRoster = doProblemFactChange( scoreDirector -> { return scoreDirector.getWorkingSolution(); });
        ShiftDate startDate = solutionBusiness.getSolution().getNurseRosterParametrization().getPlanningWindowStart();
        ShiftDate endDate = solutionBusiness.getSolution().getNurseRosterParametrization().getLastShiftDate();
        int tabSelection = -1;
        //=  doProblemFactChange( return scoreDirector -> { scoreDirector.getWorkingSolution().getNurseRosterParametrization().getPlanningWindowStart(); };)); 
        if (solutionBusiness.isSolving())
        {
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "You can not edit while solving. \n \n"
                    + "Terminate solving first.");
        }
        else
        {
            JTabbedPane tabbedPane = new JTabbedPane();
            tabDatePanel = new CustomTabDatePanel(solutionBusiness);
            tabEmployeePanel = new CustomTabEmployeePanel(solutionBusiness);
            tabShiftTypePanel = new CustomTabShiftTypePanel(solutionBusiness);
            tabContractLinePanel = new CustomTabContractLinesPanel(solutionBusiness);
            tabPatternLinePanel = new CustomTabPatternLinesPanel(solutionBusiness);
            tabMinMaxContractLinePanel = new CustomTabMinMaxContractLinesPanel(solutionBusiness);
            
            tabbedPane.addTab("Dates", null, tabDatePanel, "Change roster dates");
            tabbedPane.addTab("Contract Lines", null, tabContractLinePanel, "Change contracts");
            tabbedPane.addTab("Pattern Lines", null, tabPatternLinePanel, "Change patterns");
            tabbedPane.addTab("MinMax Contract Lines", null, tabMinMaxContractLinePanel, "Change min/max contract lines");
            tabbedPane.addTab("Employees", null, tabEmployeePanel, "Change employees");
            tabbedPane.addTab("Shift Types", null, tabShiftTypePanel, "Change shift types");
            //tabbedPane.addTab("Shifts", null, panel, "Change shifts");
            tabSelection = tabbedPane.getSelectedIndex();
            tabbedPane.addChangeListener(new ChangeListener()
            {
                public void stateChanged(ChangeEvent e)
                {
                    //JOptionPane.showMessageDialog(null, "Tab changed: " + tabbedPane.getSelectedIndex() + " with event message " + e.toString());
                    //if (tabSelection == 0 ) { //<-- should be final but then we can not assign???
                    if (!tabDatePanel.isEqual())
                    {
                        tabDatePanel.handleOKButtonClicked();  //tab change mimics OK button clicked behaviour
                    }
                    if (tabEmployeePanel.isInitalized && !tabEmployeePanel.isEqual())
                    {
                        tabEmployeePanel.handleOKButtonClicked();
                    }
                    if (!tabContractLinePanel.isEqual())
                    {
                        tabContractLinePanel.handleOKButtonClicked();
                    }
                    if (!tabMinMaxContractLinePanel.isEqual())
                    {
                        tabMinMaxContractLinePanel.handleOKButtonClicked();
                    }
                    if (!tabPatternLinePanel.isEqual())
                    {
                        tabPatternLinePanel.handleOKButtonClicked();
                    }
                    if (tabShiftTypePanel.isInitialized && !tabShiftTypePanel.isEqual())
                    {
                        tabShiftTypePanel.handleOKButtonClicked();
                    }
                }
            });
            
            final JDialog editDialogBox = new JDialog();
            BorderLayout layout = new BorderLayout();
            editDialogBox.setLayout(layout);
            editDialogBox.setLocationRelativeTo(this.getTopLevelAncestor());
            
            editDialogBox.add(tabbedPane);
            //editDialogBox.add(editDone);
            onCloseEditDialogBoxAction = new AbstractAction("Magic")
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    handleCloseEditDialogBox();
                }
            };
            editDialogBox.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosed(WindowEvent arg0)
                {
                    handleCloseEditDialogBox();
                }   
            });
            editDialogBox.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //HIDE_, DISPOSE_ or DO_NOTHING_ON_CLOSE);

            editDialogBox.pack();
            editDialogBox.setVisible(true);
            //resetShiftListPanel();
            resetPanel(solutionBusiness.getSolution());
        }
        
    }

    /* added function  waalbrugwire 23-2-2018
       Helper function for setting requiredEmployeeSize in the ShiftList
     */
    public void addEmployee(final Employee employee)
    {
        //this is the reverse of deleteEmployee
        logger.info("Scheduling add for employee ({}).", employee);
        doProblemFactChange(scoreDirector ->
        {
            NurseRoster nurseRoster = scoreDirector.getWorkingSolution();
            Employee workingEmployee = scoreDirector.lookUpWorkingObject(employee);
            if (workingEmployee != null)
            {
                // The employee has already been added, so do nothing
                JOptionPane.showMessageDialog(this.getTopLevelAncestor(), "Employee already exists. No employee was added");
                return;
            }
            
            // A SolutionCloner does not clone problem fact lists (such as employeeList)
            // Shallow clone the employeeList so only workingSolution is affected, not bestSolution or guiSolution
            ArrayList<Employee> employeeList = new ArrayList<>(nurseRoster.getEmployeeList());
            //nurseRoster.setEmployeeList(employeeList);
            // Remove it the problem fact itself
            workingEmployee = employee;
            scoreDirector.beforeProblemFactAdded(workingEmployee);
            employeeList.add(workingEmployee);
            scoreDirector.afterProblemFactAdded(workingEmployee);
            scoreDirector.triggerVariableListeners();
            nurseRoster.setEmployeeList(employeeList);
            scoreDirector.triggerVariableListeners();
        });
    }
    
    public void deleteShiftType(final ShiftType shiftType)
    {
        logger.info("Scheduling delete of ShiftType ({}).", shiftType);
        doProblemFactChange(scoreDirector ->
        {
            NurseRoster nurseRoster = scoreDirector.getWorkingSolution();
            ShiftType workingShiftType = scoreDirector.lookUpWorkingObject(shiftType);
            if (workingShiftType == null)
                return;
            
            scoreDirector.beforeProblemFactRemoved(workingShiftType);
            nurseRoster.getShiftTypeList().remove(workingShiftType);
            scoreDirector.afterProblemFactRemoved(workingShiftType);
            scoreDirector.triggerVariableListeners();
        });
    }
    
    private void generateShiftRequirements(NurseRoster nurseRoster, int dummy__put_template_here_later)
    {
        List<Shift> shiftList = nurseRoster.getShiftList();
        
        //template should be a 3 dimensional of <ShiftType, DayOfWeek, int requirement>
        for (Shift shift : shiftList)
        {
            if (shift.getShiftDate().getDayOfWeek() == DayOfWeek.SATURDAY
                    || shift.getShiftDate().getDayOfWeek() == DayOfWeek.SUNDAY)
            
            {
                if (shift.getShiftType().getCode().contentEquals("wk")
                        || shift.getShiftType().getCode().contentEquals("E")
                        || shift.getShiftType().getCode().contentEquals("L")
                        || shift.getShiftType().getCode().contentEquals("N"))
                {
                    shift.setRequiredEmployeeSize(1);
                }
            }
            if (shift.getShiftDate().getDayOfWeek() == DayOfWeek.MONDAY
                    || shift.getShiftDate().getDayOfWeek() == DayOfWeek.TUESDAY
                    || shift.getShiftDate().getDayOfWeek() == DayOfWeek.WEDNESDAY
                    || shift.getShiftDate().getDayOfWeek() == DayOfWeek.THURSDAY
                    || shift.getShiftDate().getDayOfWeek() == DayOfWeek.FRIDAY)
            {
                if (shift.getShiftType().getCode().contentEquals("1")
                        || shift.getShiftType().getCode().contentEquals("2")
                        || shift.getShiftType().getCode().contentEquals("E")
                        || shift.getShiftType().getCode().contentEquals("L")
                        || shift.getShiftType().getCode().contentEquals("N"))
                {
                    shift.setRequiredEmployeeSize(1);
                }
                else if (shift.getShiftType().getCode().contentEquals("D"))
                {
                    shift.setRequiredEmployeeSize(6);
                }
                else if (shift.getShiftType().getCode().contentEquals("DH"))
                {
                    shift.setRequiredEmployeeSize(2);
                }
                
            }
            
        }
        nurseRoster.setShiftList(shiftList);
    }
    
    private void generateShiftRequirements(NurseRoster nurseRoster)
    {
        generateShiftRequirements(nurseRoster, 0);
    }
    
    
    
    private void handleCloseEditDialogBox()
    {
        JOptionPane.showMessageDialog(this.getTopLevelAncestor(),
                "Closing the Tabbed Pane."
        );
        NurseRoster nurseRoster = solutionBusiness.getSolution();
        NurseRosteringImporter imp = new NurseRosteringImporter();
        
        List<ShiftType> currentList = nurseRoster.getShiftTypeList();
        for (ShiftType del : tabShiftTypePanel.toDeleteShiftType)
        {
            deleteShiftType(del);
        }
        
        for (Employee del : tabEmployeePanel.toDeleteEmployee)
        {
            deleteEmployee(del);
        }
        
        for (Employee add : tabEmployeePanel.toAddEmployee)
        {
            addEmployee(add);
        }
        
        LocalDate lDate01 = null;
        LocalDate lDate02 = null;
        ShiftDate firstDate;
        ShiftDate lastDate;
        firstDate = nurseRoster.getNurseRosterParametrization().getFirstShiftDate();
        lastDate = nurseRoster.getNurseRosterParametrization().getLastShiftDate();
        if (firstDate.getDate().isBefore(lastDate.getDate()))
            resetPanel(nurseRoster);
    }
    
    public SolutionBusiness getSolutionBusiness()
    {
        return solutionBusiness;
    }
    
    public JPanel getEmployeeListPanel()
    {
        return employeeListPanel;
    }
    
    public void startPrint()
    {
        JFrame f = this.getSolverAndPersistenceFrame();
        JPanel p = getEmployeeListPanel();
        PrinterJob job;
        PrintService printService;
        NurseRoster nurseRoster = solutionBusiness.getSolution();
        List<Employee> employeeList = nurseRoster.getEmployeeList();
        List<ShiftDate> shiftDateList = nurseRoster.getShiftDateList();
        List<ShiftAssignment> shiftList = nurseRoster.getShiftAssignmentList();
        ShiftAssignment t = shiftList.get(0);
        if (t.getEmployee() == null)
        { //Waalbrugwire 25-10-2018
            JOptionPane.showMessageDialog(null, "Error creating the CSV file, use 'Solve' before 'Print'");
            System.out.println("Error creating the CSV file, use 'Solve' before 'Print'");
        }
        else
        {
            FileWriter out = null;
            try
            {
                out = new FileWriter(getSolutionBusiness().getExportDataDir().toString() + "/" + "Roster" + LocalDate.now() + ".csv");
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, "Error opening the CSV file " + e);
                System.out.println("Error opening the CSV file " + e);
            }
            
            String output = "";
            char seperator = ',';
            char newLine = '\n';
            int count = employeeList.size() * shiftDateList.size();
            int subcount = shiftDateList.size(); //linecount

            for (ShiftDate headingDate : shiftDateList)
            {
                output = headingDate.getDate().toString(); //starting with a seperator before first date because the rest of the CSV starts with employee name
                try
                {
                    out.write(seperator);
                    out.write(output);
                }
                catch (IOException e)
                {
                    JOptionPane.showMessageDialog(null, "Error writing to the CSV file " + e);
                    System.out.println("Error writing to the CSV file " + e);
                }
            }
            try
            {
                out.write(newLine);
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, "Error writing to the CSV file " + e);
                System.out.println("Error writing to the CSV file " + e);
            }
            
            for (Employee employee : employeeList)
            {
                subcount = shiftDateList.size();
                //output = '"' + employee.getName() + '"' + ',';
                output = employee.getName();
                try
                {
                    out.write(output);
                    out.write(seperator);
                }
                catch (IOException e)
                {
                    JOptionPane.showMessageDialog(null, "Error writing to the CSV file " + e);
                    System.out.println("Error writing to the CSV file " + e);
                }
                for (ShiftDate shiftDate : shiftDateList)
                {
                    output = "";

                    //ShiftAssignment s = new ShiftAssignment;
                    for (int index = 0; index < shiftList.size(); index++)
                    {
                        ShiftAssignment s = shiftList.get(index);
                        if (s != null && s.getShiftDate().equals(shiftDate) && s.getEmployee().equals(employee))
                        {
                            output = s.getShiftType().getCode(); //or toString which also returns code
                            break;
                        }
                    }
                    if (employee.getDayOffRequestMap().containsKey(shiftDate))
                    {
                        output = output + 'h';
                    }
                    
                    try
                    {
                        out.write(output);
                    }
                    catch (IOException e)
                    {
                        JOptionPane.showMessageDialog(null, "Error writing to the CSV file " + e);
                        System.out.println("Error writing to the CSV file " + e);
                    }
                    if (count > 1 && subcount > 1)
                    {
                        //output = '"' + output + '"' + ',';
                        try
                        {
                            out.write(seperator);
                        }
                        catch (IOException e)
                        {
                            JOptionPane.showMessageDialog(null, "Error writing to the CSV file " + e);
                            System.out.println("Error writing to the CSV file " + e);
                        }
                    }
                    count--;
                    subcount--;
                }
                //output = newLine; // "\r\n"; //"CRLF"; //"\r\n"  //"\r\n" only works on Windows?
                try
                {
                    out.write(newLine);
                }
                catch (IOException e)
                {
                    JOptionPane.showMessageDialog(null, "Error writing to the CSV file " + e);
                    System.out.println("Error writing to the CSV file " + e);
                }
            }
            try
            {
                if (out != null)
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, "Error closing the CSV file " + e);
                System.out.println("Error closing the CSV file " + e);
            }
            
            File filePath = getSolutionBusiness().getExportDataDir();
            int result = JOptionPane.showConfirmDialog(this.getTopLevelAncestor(), "Do you want to print? \n",
                    "To Print or not to Print.",
                    JOptionPane.OK_CANCEL_OPTION);
            //, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION)
            {
                PrintUIWindow printWindow = new PrintUIWindow(p, LocalDate.now());
                job = PrinterJob.getPrinterJob();
                printService = job.getPrintService();
                
                job.setPrintable(printWindow);
                
                try
                {
                    job.print();
                    JOptionPane.showMessageDialog(null, "Document created in :\n" + getSolutionBusiness().getExportDataDir().toString());
                }
                catch (PrinterException ex)
                {
                    /* The job did not successfully complete */
                    JOptionPane.showMessageDialog(null, "Printjob not succesful. \n"
                            + "Error : " + ex);
                }
            }
        }
    }
}
