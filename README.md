# Course Analyzer

## A quantitative approach for course selection.

### Proposal ###
- **What will the application do?  Who will use it?**

    As students, when choosing a course or deciding the section of a course, we always try to choose the one that looks 
    like the "easiest" one to get a good grade. Even though easiness of a course is a subjective concept, there are some 
    numerical data that can help us to make that decision, like previous averages, and grade distributions. However, 
    collecting this data is a time consuming process, and it is likely to do mistakes while collecting the data and 
    comparing it with the data of previous years. The purpose of this application is to automate this process and 
    provide useful insights into a course section that was provided by the user. Targeted users for this application are 
    UBC undergraduate students who try to choose one of the current sections of a course that they want to enroll. 
    To use the application, the user will input the subject area, course number, and the section number. After this,
    the application will get the data for the last five years, and filter it for the given section's professor, As 
    an output, the user will see the class average of the last five year for that section's professor, and other useful
    insights like grade distributions, pass rates, and data visualizations. Also, the user will be able to save these
    results in a list, therefore, the user will be able to compare the data for different sections and make a decision 
    based on this data.

- **Why is this project of interest to you?**
    
    At the beginning of the year, one of the greatest challenges that I had was to choose the right courses that I can
    succeed. To identify these courses, first, I found the courses that I should take. After that, I find the 
    instructors for the courses and look at the class averages and grade distributions of their class in the 
    previous years. This was a time consuming process for me. When I came to UBC, I noticed that most of my friends
    went through the same process as me. That moment, I noticed that this process is a systematic and iterative process.
    Therefore, I wanted to automate this process by writing a desktop application. This way, it would be much faster and
    easier for me and my friends to choose our courses.
    
### User Stories ###

- As a user, I want to be able to see a plot for the class average data of a course in five years.

- As a user, I want to be able to see a bar chart for the grade distribution data of a course in five years.

- As a user, I want to be able to get the five year average for the course that I input, thought by the professor of the 
section that I input.

- As a user, I want to be able to add a course and its data to a course list.

- As a user, I want to be able to remove a course from the course list.

- As a user, I want to be able to add multiple courses to a course list, and view the list to compare the data for 
different courses and/or sections.

- As a user, when I select the quit option from the menu, I want the option to save my course list for 
future use.

- As a user, when I start the application, I want the option to load my course list from the my previous usage.

### Phase 4: Task 2 ###

The option that I chose: Make appropriate use of the Map interface somewhere in your code. 

I used the Map interface in my Course class. Now, there is a new field in Course that is named grade distributions. 
This field of Course holds the grade distribution between students for certain grade ranges. 
For example, 90-100%: 120 etc.
This means that there is 120 student who took this course in the past five years from that professor get a grade between 
90-100%. I used a LinkedHashMap because the order of the grade ranges are crucial for this field. The reason is, by 
using this field, now in the Course page in the GUI, you can find a bar chart that visualizes the distribution of the 
grades. 

### Phase 4: Task 3 ###
Identified Problems & Improvements :
- CoursePage class is the GUI class that shows the details of a course like its name, average, and its data 
visualizations. I noticed that this class also creates the data visualization panels and also handles and performs the 
related tasks. This violates the Single Responsibility Principle, therefore it causes poor cohesion. To solve this issue 
by improving cohesion, I created two new classes, FiveYearAverageGraph and GradeDistributionChart. These classes handle
and perform the creation of the data visualization and the related tasks to this. Therefore, it improves cohesion by 
following the Single Responsibility Principle.

-  CourseList class is used by all of the GUI classes other than the Main class which only has the welcome page. All of
the classes are using the same CourseList, so they used to have a CourseList parameter in their constructor and pass it 
and instantiated it any time that we changed the page. However, there should be only a single instance of that class 
because the CourseList is same throughout the execution of the program. Also, having a global point of access for that 
single instance will make everything easier because that class will be used by many classes. Besides, this problem
causes the classes to violate the Single Responsibility principle, and to have a low cohesion. The reason is, the classes
that use CourseList class has to instantiate the course list, make sure that it is the same across all of the pages, and 
have to pass the course list to the other pages. So, to solve this problem, I applied Singleton Design Pattern to my 
CourseList class. Making this change  improves cohesion because now the other GUI classes doesn't have to ensure that 
the CourseList is the same and its content don't change during the page transitions. 
     
### Instructions for Grader ###

#### Demo Video For Guidance ####
[![Demo Video](http://img.youtube.com/vi/mv7jDfW8vRw/0.jpg)](https://youtu.be/mv7jDfW8vRw)

- New feature: in the course page, now you can switch the graphs by using the switch graph button.

- You can generate the loading a saved course list event by clicking the "Load Data From Previous Session" button.

- You can save the given course list by clicking the "Save Course List" button under the saved courses overview panel.
Also, if you try to quit the application by pressing "x", application will ask you if you want to save the course list.

- You can analyze the course and get the visual component by inputting the course information to the fields and pressing 
to "Start" button. Also, you can generate the "add x to y" function in this page by pressing "Add to Course List" 
button. If you do so, it will be added to the course list and it will be visible in the panel. However, you have to save
the course list if you want to access that list when you close the application. Some visually rich courses are:
    - CPSC 121 202
    - PSYC 102 001 
    - MATH 200 101

- If you press the "Show Detailed Course List" button, you can see all of the courses in your course list with their 
data. 

- In the "Detailed Course List" page, you can generate the first two required events:
    - If you click "Remove Selected Course" button, it will remove the course from the list. 
    (You have to save the course list if you want to remove that course permanently)
    - If you click "View Course Details" button, you can see the "Course Page" for the course you chose from the panel.