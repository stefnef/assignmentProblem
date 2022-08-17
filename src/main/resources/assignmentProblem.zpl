set Students := { read "students.data" as "<1s>" comment "#" };
set Subjects := { read "subjects.data" as "<1s>" comment "#" };
set Connections :={ <s,k> in Students * Subjects };

param c[Subjects] := read "subjects.data" as "<1s> 2n" comment "#";
param p[Connections] := read "priorities.data" as "<1s,2s> 3n" comment "#";

defset SubjectsOfStudent(s) := {<s, i> in Connections};
defset StudentsOfSubject(k) := {<i, k> in Connections};

var x[Connections] binary;

maximize satisfaction: sum<i,j> in Connections: p[i,j] * x[i,j];

subto capacitiesOfSubjects:
    forall <k> in Subjects:
        sum<i,k> in StudentsOfSubject(k): x[i,k] <= c[k];

subto chosenSubjectsOfStudents:
        forall <s> in Students:
            sum<s,i> in SubjectsOfStudent(s): x[s,i] == 1;
