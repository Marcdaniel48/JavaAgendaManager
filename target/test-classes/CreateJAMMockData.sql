USE JAMDB;

-- Insert data for SMTP_SETTINGS -- *Ask about what data to Insert
INSERT INTO SMTP_SETTINGS values
(NULL, 'JAMnotifications', 'JAMEmailReminder@gmail.com', 'thunderboltx', 'smtp.gmail.com', DEFAULT, 1),
(NULL, 'Air', 'air_is_the_best201999@gmail.com', 'hunter2', 'smtp.gmail.com', DEFAULT, DEFAULT);

-- Insert date for GROUP_RECORD table
-- Colors: orange, blue, green, red, grey
INSERT INTO GROUP_RECORD values
(NULL, 'work/school', '#ffa500'),
(NULL, 'events', '#0000ff'),
(NULL, 'personal', '#00ff00'),
(NULL, 'urgent', '#ff0000'),
(NULL, 'other', '#808080');

-- Insert data for APPOINTMENT table -- *Ask about the default value for END_TIME. *Ask about the primary key (should they
-- be auto incremented?)
INSERT INTO APPOINTMENT values
(NULL, "Dentist appointment", "The Dentist's Office", "2018-01-20 14:00:00", NULL, "Gotta go to the dentist", 0, 1, 10, 1),
(NULL, "Doctor's appointment", "The Hospital", "2018-01-22 14:00:00", NULL, "I'm coughing too much", 0, 3, 3, 1),
(NULL, "Friend's birthday", "Friend's house", "2018-01-23 15:00:00", NULL, "", 1, 2, 2, 1),
(NULL, "Math Test #1", "School", "2018-01-24 10:00:00", "2018-01-24 11:15:00", "Study review sheet", 0, 1, 0, 0),
(NULL, "Second English Exam", "School", "2018-01-25 10:00:00", "2018-01-25 11:15:00", "Easy pz", 0, 1, 0, 0),
(NULL, "Work", "Workplace", "2018-01-26 13:00:00", "2018-01-27 20:00:00", "", 0, 1, 0, 0),
(NULL, "Drinking Event", "The Local Bar", "2018-01-27 21:00:00", NULL, "", 0, 2, 0, 0),
(NULL, "Video Game Tournament", "A large building", "2018-01-28 17:00:00", "2018-01-28 20:00:00", "CSGO", 0, 2, 1, 1),
(NULL, "Basketball match", "Basketball court", "2018-01-29 13:30:00", "2018-01-29 14:30:00", "vs School#4", 0, 5, 1, 1),
(NULL, "Club meeting", "Club room", "2018-01-30 15:00:00", NULL, "Discussion concerning important things", 0, 4, 2, 1),
(NULL, "Game of Thrones Marathon", "Friend's house", "2018-01-31 10:30:00", "2018-01-31 19:30:00", "", 0, 2, 0, 0),
(NULL, "Anime night", "My house", "2018-02-01 19:00:00", "2018-02-01 23:30:00", "", 0, 5, 1, 0),
(NULL, "Gym", "The Gym", "2018-02-02 10:00:00", "2018-02-02 11:00:00", "", 0, 5, 0, 0),
(NULL, "Amazon delivery", "Home", "2018-02-03 12:30:00", NULL, "New phone case!!", 0, 5, 0, 0),
(NULL, "Watch movie", "The cinema", "2017-09-12 16:00:00", NULL, "New fantasy movie", 0, 5, 0, 0),
(NULL, "Clean house", "Where I live", "2017-09-07 9:30:00", NULL, "", 0, 5, 0, 0),
(NULL, "Supper with the family", "Parent's house", "2017-10-19 19:00:00", NULL, "Bring pasta", 0, 5, 1, 1),
(NULL, "Attend the mass", "Church", "2017-09-24 10:00:00", "2017-09-24 11:00:00", "", 0, 5, 0, 0),
(NULL, "Cousin's marriage", "Marriage location", "2018-02-03 14:30:00", NULL, "", 0, 5, 1, 1),
(NULL, "School", "School", "2018-02-05 8:30:00", "2018-02-05 16:00:00", "", 0, 1, 1, 0),
(NULL, "No school today", "none", "2018-02-06 00:00:00", NULL, "", 1, DEFAULT, DEFAULT, DEFAULT),
(NULL, "School", "school", "2018-02-07 8:30:00", "2018-02-05 16:00:00", DEFAULT, 0, 1, DEFAULT, DEFAULT),
(NULL, "Sleep all day", "home", "2018-02-08 00:00:00", NULL, "zzz...", 1, 5, DEFAULT, DEFAULT),
(NULL, "Buy groceries", "grocery store", "2018-02-09 11:00:00", NULL, "Check the list", 0, DEFAULT, DEFAULT, DEFAULT),
(NULL, "Assignment #1 due", "nowhere", "2018-02-09 23:59:00", NULL, DEFAULT, 0, DEFAULT, DEFAULT, DEFAULT),
(NULL, "Go to Rick's house", "Rick's house", "2018-02-10 16:45:00", NULL, "", 0, 5, 0, 0),
(NULL, "baseball match", "baseball arena", "2018-02-11 14:30:00", NULL, "", 0, DEFAULT, 1, 1),
(NULL, "In-class essay", "at school", "2018-02-12 10:00:00", "2018-02-12 11:15:00", DEFAULT, 0, 4, DEFAULT, DEFAULT),
(NULL, "lunchbreak", "at school", "2018-02-12 13:00:00", "2018-02-12 13:45:00", DEFAULT, 0, DEFAULT, DEFAULT, DEFAULT),
(NULL, "Go to the gym", "The gym", "2018-02-12 18:30:00", "2018-02-12 19:30:00", DEFAULT, 0, 5, 0, 0),
(NULL, "Go to the zoo with family", "The zoo", "2018-02-13 14:00:00", NULL, DEFAULT, 0, DEFAULT, DEFAULT, DEFAULT),
(NULL, "Watch Disney movie with family", "The cinema place", "2018-02-13 18:00:00", NULL, DEFAULT, 0, DEFAULT, DEFAULT, DEFAULT),
(NULL, "Music concert", "Concert hall", "2018-02-14 17:00:00", NULL, DEFAULT, 0, DEFAULT, DEFAULT, DEFAULT),
(NULL, "Visit brother at hospital", "Kid's hospital", "2018-02-15 12:30:00", NULL, DEFAULT, 0, DEFAULT, DEFAULT, DEFAULT),
(NULL, "Do nothing on Friday day", "", "2018-02-16 00:00:00", NULL, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT),
(NULL, "Play video games", "", "2018-02-16 9:00:00", NULL, DEFAULT, 0, DEFAULT, DEFAULT, DEFAULT),
(NULL, "Work on project", "at home", "2018-02-17 12:45:00", NULL, DEFAULT, 0, DEFAULT, DEFAULT, DEFAULT),
(NULL, "Study for other subjects", "at home", "2018-02-18 12:45:00", NULL, DEFAULT, 0, DEFAULT, DEFAULT, DEFAULT),
(NULL, "Volunteering", "Volunteer place", "2018-02-19 10:00:00", "2018-02-19 14:00:00", DEFAULT, 0, DEFAULT, 1, 1),
(NULL, "Pop culture convention", "Convention center", "2018-02-20 10:30:00", "2018-02-20 22:30:00", DEFAULT, 0, 2, 2, 1),
(NULL, "Pickup siblings", "Daycare", "2018-02-21 15:25:00", NULL, "", 0, 4, DEFAULT, DEFAULT),
(NULL, "Buy new computer", "The computer store", "2018-02-22 14:00:00", NULL, "GAMING COMPUTER", 0, DEFAULT, 1, 1),
(NULL, "clothes shopping", "Winners", "2018-02-23 16:00:00", NULL, DEFAULT, 0, DEFAULT, DEFAULT, DEFAULT),
(NULL, "Buy keyboard", "Best Buy", "2018-02-24 15:30:00", NULL, DEFAULT, 0, DEFAULT, DEFAULT, DEFAULT),
(NULL, "Hospital appointment", "The Hospital", "2018-02-25 13:00:00", NULL, "Back hurts", 0, 3, 1, 1),
(NULL, "Go karting", "Go kart place", "2018-02-26 13:30:00", "2018-02-26 14:30:00", DEFAULT, 0, 2, DEFAULT, DEFAULT),
(NULL, "Play at the arcade", "Arcade", "2018-02-26 14:30:00", NULL, DEFAULT, 0, 2, DEFAULT, DEFAULT),
(NULL, "Cleanup room", "My room", "2018-02-27 8:00:00", NULL, DEFAULT, 0, DEFAULT, DEFAULT, DEFAULT),
(NULL, "Guitar lessons", "Music school", "2018-02-28 10:00:00", "2018-02-28 11:00:00", "", 0, 5, 1, 1),
(NULL, "Code android app", "anywhere", "2018-03-01 13:00:00", NULL, "Personal project", 0, DEFAULT, DEFAULT, DEFAULT);