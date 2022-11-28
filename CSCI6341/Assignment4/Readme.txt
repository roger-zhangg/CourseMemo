To run the file, 
1: enter works directory

	cd works/

2: Use the following command

	java ThreeServerQueue {K} {lambda}

3: For example, if you want a model with 3 queue and 3.1 as arrival rate, use

	java ThreeServerQueue 3 3.1

4: If you want a modle with 2 queue and 2.0 as arrival rate, use

	java ThreeServerQueue 2 2.0

5: Example Run

/CourseMemo/CSCI6341/Assignment4/works# java ThreeServerQueue 2 2.0
After 100000 departures: avgWait=72.75412274624783  avgSystemTime=73.74751441237873