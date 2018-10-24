# Roster-Planner

Contains GUI add-ons to Opta Planners NurseRostering example (7.5.0 Final).
The add-ons mainly consist of Tabbed panes to make the example editable.

Currently there are tabbed panes to edit the roster's
- begin and end dates
- Employees including DayOffRequests
- ShiftTypes
- Contracts
- PatternLines

- (under construction) MinMaxContractLines
- (to be done) BoolContractLines

The project was build under NetBeans IDE 8.2 
We used the supplied Maven pom.xml and adjusted to our needs to build a One.jar aka Fat.jar

The files consist of a a TabXXXPanel and a CustomTabXXXPanel file
where XXX stands here for an Entity or logical component of the program.

The Tab files mainly contain the generated TabPanes using the visual editor
(with some generated/edited basic getter, setters and handlers to override) and represent the View part

The CustomTab files contain the logic and are the Controllers

The doamin part is largely unchanged.
We added some helper functions to some of the Domain Entities and several minor code changes to the SwingUI component
to integrate our Tabbed Panes.

All our changes to the original code is (or should be) commented containing a reference to WaalbrugWire and a date. 
The Tabs and CustomTabs are all made by WaalbrugWire.

Licenses. For the original part of the code refer to the OptaPlanner licenses, still left unchanged in the original code.
For the code WaalbrugWire changed or augmented the same license conditions apply except we are the owner of the code.
Excluded from open source use is any code, rule set or input file created by WallbrugWire that contain implementations
for the solution for our customer.

Dependencies: uses LGoodDatePicker library

Build  (untested): download the 7.5.0 Final sources including examples/nurserostering and add our TabXXXPanel and CustomXXXTab files
into the swingui directory. Adjust EmployeePanel and NurseRosteringPanel or replace them with our version
