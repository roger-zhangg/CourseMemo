grade( Student, Grade):-
    hw1(Student,Ghw1),
    hw2(Student,Ghw2),
    hw3(Student,Ghw3),
    exam1(Student,Gex1),
    exam2(Student,Gex2),
    project(Student,Gproj),
	(Grade is 0.2*(Ghw1+Ghw2+Ghw3)/3+0.4*(Gex1+Gex2)/2+0.4*Gproj).

letterGrade( Grade,  LG ):-
    (Grade >= 90 ->  LG = 'A'
    ;  Grade >= 85 ->  LG = 'A-'
    ;  Grade >= 80 ->  LG = 'B+'
    ;  Grade >= 75 ->  LG = 'B'
    ;  Grade >= 70 ->  LG = 'B-'
    ;  Grade >= 65 ->  LG = 'C+'
    ;  Grade >= 60 ->  LG = 'C'
    ; LG = 'F'
    ).

studentLetterGrade( Student,LG):- grade(Student,Grade),letterGrade(Grade,LG).
