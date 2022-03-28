package de.shiirroo.islands.command;

import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {

    private final String name;
    private boolean isNeedOP = false;
    private final ArrayList<CommandBuilder> sCBList = new ArrayList<>();
    private boolean customInput = false;


    public CommandBuilder(String name){
        this.name = name;
    }

    public CommandBuilder(String name, Boolean OP){
        this.name = name;
        this.isNeedOP = OP;
    }

    public CommandBuilder setCustomInput(){
        this.customInput = true;
        return this;
    }


    public boolean isCustomInput() {
        return customInput;
    }

    public boolean hasSubCommands(){
        return this.sCBList.size() > 0;
    }


    public ArrayList<String> getSubCommandListAsString(Boolean isOP){
        ArrayList<String> subCommand = new ArrayList<>();
        for(CommandBuilder subCommandBuilder :this.sCBList){
            if(subCommandBuilder.isNeedOP == isOP || isOP)
                subCommand.add(subCommandBuilder.getCommandName());
        }
        return subCommand;
    }


    public String getCommandName() {
        return this.name;
    }


    public CommandBuilder getSubCommand(String command, Boolean isOP) {
        for(CommandBuilder subCommandBuilder :this.sCBList){
            if(subCommandBuilder.getCommandName().equalsIgnoreCase(command))
                if(subCommandBuilder.isNeedOP == isOP || isOP)
                    return subCommandBuilder;

        }
        return null;
    }



    public void addSubCommandBuilder(CommandBuilder subCommand){
        this.sCBList.add(subCommand);
    }


}
