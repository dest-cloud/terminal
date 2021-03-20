# terminal

Terminal with list of predefined commands. Create your list of commands, the variables that you want to replace, and you
are good to run your favourite commands all the time.

# Install

1. Create a folder called .console in your home directory.
2. Create a file variables.json with the following content (please change the username 'me')

```json
[
  {
    "variable": "COMMAND_FILE",
    "value": "/home/me/commands/commands.json"
  },
  {
    "variable": "TITLE:term1",
    "value": "My first terminal"
  },
  {
    "variable": "home_dir",
    "value": "/home/me/.console"
  }
]
```

3.Create a command file called commands.json in /home/me/commands/commands.json

```json
[
  {
    "name": "List content in .console",
    "alias": "lst",
    "command": "list -al ${home_dir}",
    "shellType": "",
    "consoleId": "term1",
    "execBefore": "",
    "execAfter": ""
  }
]
```

4. Run the jar

```bash
java -jar terminal-1.0-SNAPSHOT.jar
```

In the 'Configs' menu select your variables.json file. Your first command should be available in the list. As soon as you run
your command you should see the list of files in '.console'.

# Variables

In your '.console' directory you can create as many json files as you want. A good practice is to make a directory when
you want to create multiple profiles for a same command file. For instance, if you have two projects that needs to run
the same commands but in different directories or with different versions you can create one command file and multiple
variable files.

The hierarchy in the '.console' directory will be available in the 'Configs' menu. As soon as you select a json file it will
be loaded with the corresponding command file. The commands will be loaded in the list, and the variables will be
replaced in the commands.

Example of variables:

```json
[
  {
    "variable": "simple_variable",
    "value": "Simple value"
  },
  {
    "variable": "second_variable",
    "value": "Second value"
  }
]
```

Note: a variable file needs to have a '.json' extension.

## Set the command file

In your variable file, as a good practice set the first variable as the location of your command file. It should be:

```json
[
  {
    "variable": "COMMAND_FILE",
    "value": "/home/me/commands/commands.json"
  }
]
```

## Change title in tab

You can set the title of the terminal tab. When you run a command, you can specify an id for the terminal in which you
want to execute the command. In a variable file you can set the title that will replace the id.

```json
[
  {
    "variable": "TITLE:consoleId",
    "value": "/home/me/commands/commands.json"
  }
]
```

# Commands

A command file defines a set of commands that can be executed in the terminal. The command file needs to have a '.json'
extension, but it can be stored anywhere as long as it is correctly referenced in a variable file.

```json
[
  {
    "name": "Command name",
    "alias": "alias",
    "command": "command",
    "shellType": "bash",
    "consoleId": "id",
    "execBefore": "",
    "execAfter": ""
  }
]
```

1. name => The name of the command as it will appear in the list
2. alias => An alias or shortcut that you can type in the text field
3. command => The command itself
4. shellType => The shell type you want to launch (not available yet)
5. consoleId => An id if you want to execute some commands in the same terminal.   
6. execBefore => The name of the command you want to execute before this one (not available yet)
7. execAfter => The name of the command you want to execute after (not available yet)

## Replace variable in command
In the command attribute of a command you can set a token to replace the token with the corresponding value.
If your variable is called myVar, and the value '/home/me'
```json
...
 "command": "cd ${myVar}"
...
```
When you load the variables, the command will be replaced (in memory not in the file) by:
```json
...
 "command": "cd /home/me"
...
```

## Undefined variable
If a token in a command is not found among the variables, a dialog will ask you to set the value when you run the command.
This is very powerful if you need to execute a command and need to ask the user some information that can change everytime.
For instance, the following command:
```json
...
 "command": "git commit -m \"${message}\""
...
```
If no variable is called message, a dialog will ask everytime the user to set a message when committing in git.

## Reusing the same terminal
In case you want to execute many commands in the same terminal simply set the attribute consoleId with a same id.
```json
...
 "consoleId": "term1"
...
```
Every command that will have the consoleId 'term1' will be executed in the same terminal. When you run a command for the first time, the terminal will be opened.
The next time, the terminal will be selected automatically.

The same works if you double-click on a command or if you type its alias in the text field in the bottom.

# Text field in the bottom
The text field in the bottom is there to offer an interesting alternative to double-clicking the commands in the list or directly typing in the terminals.
You can either type any command, and it will be executed in the selected terminal. Or you can type an alias of a command set by:
```json
...
 "alias": "myAlias"
...
```
This way, if you type myAlias in the text field, the corresponding command will be executed in the right terminal (if consoleId is set).
It offers a fast way to run lots of commands.

An alias works the same as double-clicking a command in the list, the variables will be replaced as well, or a dialog will ask you to set the undefined variables.