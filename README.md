# assignmentProblem

# Usage

## Input Files

In order to run the programm you need two input files.
A file containing all priorities and a file containing all
capacities of the subjects.

You can find sample files:
- in src\test\files\priority\priorities.csv
- in src\test\files\capacity\capacities.csv

**NOTE**: All csv files expected to be comma separated!

### Priority File
The priority file contains all priorities in the following csv format:

- The header line need to hold the following values:

`<StudentName>,<Year>,<SubjectName 1>,<SubjectName 2>,...`

- All following lines list the priorities of a specific student:

`<StudentName 1>,<Year>,<Priority of Subject 1>, <Priority of Subect 2>,...`

`<StudentName 2>,<Year>,<Priority of Subject 1>, <Priority of Subect 2>,...`

### Capacity File
The capacity file contains all subjects with their maximal number of students (capacities) in the following csv format:

- The header line need to hold the following values:

`<SubjectName 1>,<SubjectName 2>,...`

- The second line list the capacities:

`<Capacity of SubjectName 1>,<Capacity of SubjectName 1>,...`

## Using JarFile
`java -jar assignmentProblem-<version>.jar --prio=<priority file> --cap=<capacity file>`

## Output
If successful all output files can be found in the directory `output`.
