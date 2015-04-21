# Installation #
  * Install either Eclipse or NetBeans depending on preference.

## NetBeans ##
  1. Team>Subversion>Checkout
  1. 
    * Repository URL: https://amity-apcs-project-x-2011.googlecode.com/svn/
    * Username: Your email address
    * Password: Get it [here.](https://code.google.com/hosting/settings)
    * _Click Next_
  1. 
    * Repository Folder: Project X
    * Click Finish
  1. A dialog box should pop up. Click "Open Project."
  1. Do tab setup according to TabSetup.

## Eclipse ##
  1. Install Subversive using Help>Install...: http://community.polarion.com/projects/subversive/download/eclipse/archive/I20091009-1900/
  1. File>Import>SVN>Project From SVN
  1. If it asks, "create new repository location"
  1. URL: https://amity-apcs-project-x-2011.googlecode.com/svn/Project X
  1. 
    * Username: Your email address
    * Password: Get it [here.](https://code.google.com/hosting/settings)
    * Check "Save Password"
    * Click Next
  1. Click "Finish"
  1. Select "Check out as a project configured..."
  1. Select "Java Project"
  1. 
    * Call it "Project X"
    * Make sure Project Layout is using separate folders
  1. Click Finish
  1. Do tab setup according to TabSetup.

## Command Line ##
  1. `svn checkout https://amity-apcs-project-x-2011.googlecode.com/svn/ amity-apcs-project-x-2011`
    * Use `--username` if your username is not the same as your local username. This is very likely.
    * Use `--password` if you don't mind your password being saved to your history. Since it's just a random string, this is also very likely.
    * So, it would look like `svn --username fred@spamavert.com --password abcdefg checkout https://amity-apcs-project-x-2011.googlecode.com/svn/ amity-apcs-project-x-2011`
  1. `cd amity-apcs-project-x-2011/trunk`
    * This just gets to the code; it's not necessary to do so.
    * You can also use `amity-apcs-project-x-2011/Project\ X`, but typing in that space is a pain, and make the fact that I created a symlink "trunk" kind of pointless.
  1. Do tab setup according to TabSetup.