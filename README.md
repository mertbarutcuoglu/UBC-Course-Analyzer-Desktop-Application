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

- As a user, I want to be able to get the five year average for the course that I input, thought by the professor of the 
section that I input.

- As a user, I want to be able to add a course and its data to a course list.

- As a user, I want to be able to remove a course from the course list.

- As a user, I want to be able to add multiple courses to a course list, and view the list to compare the data for 
different courses and/or sections.

- As a user, when I select the quit option from the menu, I want the option to save my course list for 
future use.

- As a user, when I start the application, I want the option to load my course list from the my previous usage.


### Instructions for Grader ###

## Demo Video For Guidance ##
<iframe width="560" height="315" src="https://www.youtube.com/embed/ODAVOFRfm9I" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

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