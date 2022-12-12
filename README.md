# CS360-Mobile-Architect-Programming

Briefly summarize the requirements and goals of the app you developed. What user needs was this app designed to address?

  The app required that a simple Inventory based app be implemented that would have abiltiy of using a database to store items for the app to keep track 
  of. The app would have to be able to take the items from the database and display it in a grid. It should also allow the user to add, edit the  
  quantity, or remove items whenever they wish. It should allow the user to login in app or create a new account all using the login screen. The final 
  requirement is that the user should be able to enable SMS messages privaledges and the app should prompt a permission request for SMS Messages and 
  continue allowing useof the app regardless of answer but only disable SMS Messages if the user denies permission.
  
What screens and features were necessary to support user needs and produce a user-centered UI for the app? How did your UI designs keep users in mind? 
Why were your designs successful?

  At least 2 screens were needed to provide the basic functionality of the app, a login screen and the main inventory grid screen. Buttons were used to 
  perform all othe required functions. All buttons followed a simple design pattern such as being the same puprle color or using easy-to-understand icons 
  such as the pencil icon for the edit button or trash icon for delete button. All buttons were easy to identify, the user would be able to quickly 
  determine that they were buttons which made the design successfull as it was simple to use for any user regardless of tech experience. 

How did you approach the process of coding your app? What techniques or strategies did you use? How could those be applied in the future?

  The process began with first developing the login screen and all features necessary for that screen like the new account creation feature. Once that 
  was finished and tested, the main screen was developed. Frequent testing was done on a per-feature basis which helped reduce time debugging. I also 
  saved a copy of the app whenever a big milestone was achieved which helped quickly start over from a critical 'save point' in case I got overwhelmed. 
  This technique was beneficial on a few cases and saved time and headche from debugging or undoing big changed. That technique could be used in the 
  future to help either prevent loss of progress if the app became to overwhelmingly complex. However, this could be problemetic if the project is too 
  big which in that case, only certain files would be backed up. Using the log system to help the debuggig process was not used much here but it would 
  have made the development process easier, this technique will now be thoroughly utilized in the future since I found it to be to useful not to  
  implement. 
  
How did you test to ensure your code was functional? Why is this process important and what did it reveal?
  
  Testing was done regularly throughout the process whenver a new feature is being developed. This method allowed for bugs to quickly be found and
  resolved. Overall time and headache on debugging was minimized thanks to this method of testing. 

Considering the full app design and development process, from initial planning to finalization, where did you have to innovate to overcome a challenge?

  The requirements were somewhat vague on how the data from the database should be displayed on the screen with the only requirment being in grid format.
  There were no specifics on how that could be done so I had to research what was the best way to accomplish that. I look at inventory apps in the app 
  store or through Google images and tried to find the names of the mechanics used there. I eventually found out that the RecyclerView layout was the 
  answer I was looking for which allowed to further research how to implement that into the application. Implementing the recycler view function to display 
  the items from the inventory was the most difficult function as I knew absolutely nothing about it. I read through many online articles and videos on 
  how to implement it. It eventually did work out and I am quite satisfied with the result.
  
In what specific component from your mobile app were you particularly successful in demonstrating your knowledge, skills, and experience?

  The recycler view component that took items from the database and displayed it in grid view best demonstrated my skills, knowledge and experience.
  My debugging, research, creativity, and problem solving skills were put to the test to implement a feature that, up to this point, I did not even know
  existed. Some of the problems I encountered were not found when searching for it in online resources like Stack Overflow and I was still able to solve it 
  on my own which I am very proud of and helped me realize how far I have come since the beginning of college. 
