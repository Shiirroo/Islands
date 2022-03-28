package de.shiirroo.islands.command.subcommands.chunk;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.command.CommandBuilder;
import de.shiirroo.islands.command.SubCommand;
import de.shiirroo.islands.gamedata.game.chunk.GameBlock;
import de.shiirroo.islands.utilis.SaveChunk;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class AddChunk extends SubCommand {
    @Override
    public String getName() {
        return "AddChunk";
    }

    @Override
    public String getDescription() {
        return "Add the Chunk data to the game";
    }

    @Override
    public String getSyntax() {
        return "/" + IslandsPlugin.getPlugin().getName() + " addChunk";
    }

    @Override
    public Boolean getNeedOp() {
        return true;
    }

    @Override
    public CommandBuilder getSubCommandsArgs(String[] args, Player player) {
        return null;
    }

    @Override
    public void perform(Player player, String[] args) {
        Chunk chunk = player.getChunk();
        ChunkCreatorData chunkCreatorData = new ChunkCreatorData();
        for(int x = 0; x < 16;x++){
                for(int y = 50;y < 80;y++){
                    for(int z = 0; z < 16;z++){
                    if(!chunk.getBlock(x,y,z).getBlockData().getMaterial().isAir() && !chunk.getBlock(x,y,z).getBlockData().getMaterial().equals(Material.BARRIER)) {
                        chunkCreatorData.addGameBlock(new GameBlock(chunk.getBlock(x, y, z).getBlockData().getAsString(), x, y, z));
                    }
                }
            }
        }
        SaveChunk.save(chunkCreatorData);
    }
}
