# Using It #
## NetBeans ##
  1. Always go Team>Subversion>Update before editing.
  1. Make any changes you would like.
  1. To send the changes out, go Team>Subversion>Commit
    1. If you get an error, this means someone else has edited the same file.
    1. Go Team>Subversion>Update. If the problems are trivial, this might correct them. More likely, you need to manually correct this. The source files will show both new and old versions.
    1. 
```
		System.out.println("underwear"+chicken());
<<<<<<< .mine
		//The local copy
=======
		//Revision 19 (latest) on the server
>>>>>>> .r19
```
    1. After fixing any conflicts, go Team>Subversion>Resolve Conflicts... and commit again
      * If you get the same error again, then our organization has gone horribly agley and you should probably just file a new issue, labels Type-Other, Maintainability.

## Eclipse ##
  1. Always go Right Click Project>Team>Update before editing.
  1. Make any changes you would like.
  1. To send the changes out, go Right Click Project>Team>Commit
    1. If you get an error, this means someone else has edited the same file.
    1. Right Click Project>Team>Update. If the problems are trivial, this might correct them. More likely, you need to manually correct this. The source files will show both new and old versions.
    1. 
```
		System.out.println("underwear"+chicken());
<<<<<<< .mine
		//The local copy
=======
		//Revision 19 (latest) on the server
>>>>>>> .r19
```
    1. After fixing any conflicts, go Right Click Project>Team>Mark as Merged and commit again
      * If you get the same error again, then our organization has gone horribly agley and you should probably just file a new issue, labels Type-Other, Maintainability.

## Command Line ##
  1. Always `svn up` before editing.
  1. To send the changes out, `svn ci`
    * Use `-m` or `--message` to input a commit message.
    1. If you get an error, this means someone else has edited the same file.
    1. `svn up`. If the problems are trivial, this might correct them. More likely, you need to manually correct this. The source files will show both new and old versions.
    1. 
```
		System.out.println("underwear"+chicken());
<<<<<<< .mine
		//The local copy
=======
		//Revision 19 (latest) on the server
>>>>>>> .r19
```
    1. `svn resolve` can be used to select one or the other; `svn help resolve` for more details.
    1. Otherwise, edit it to resolve the conflict, then `svn resolve --accept working`
      * If you're a wizard, you can do everything manually, then `svn resolved` the file.
        * You are not a wizard.
    1. Now run the same `svn ci` you were doing.
      * If you get the same error again, then our organization has gone horribly agley and you should probably just file a new issue, labels Type-Other, Maintainability.