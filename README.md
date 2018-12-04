# Zapytaj.onet Spambot + UpvoteBot

Bot was made on demand to spam answers and upvote them on Zapytaj.Onet website

Bot was made with Java using Selenium WebDriver, working on multiple threads and multiple browsers / webdrivers.

## Instruction:

1. Configure bot in config.txt

2. Run the bot

## How does it work

1. Application takes config from file `config.txt`

2. For each account specified (main answering + upvote accounts) application creates new chrome driver and launches them with separate user account

3. For each driver made application log in to account

4. Main account goes to category specified in config and paste answer in every question.

5. After question has been answered, bot adds link to specified file contatining all answered questions ( to prevent repeating answer in the same question)

6. Bot waits specified time to prevent anti-flood and then adds link to list of new answered questions.

7. If there is any link in new answered questions, upvoting part of the application takes it and sends signal
 to all upvote-accounts' drivers to enter the link and upvote comment answered by main account.

8. Cycle repeats until all answers in the specified category have been answered.

## Changelog

### v 1.0 - SNAPSHOT

- Fully functional version of a bot, waiting for client to make final change orders.

### v 1.0

- Added checking for popup to dismiss when logging in - on multiple browsers popup sometimes does not open on first page.

- Prevented bot from crashing when question was created as a survey



