package me.linckode.phones;

import org.bukkit.entity.Player;

public class ActionLinker {

    private MessageAction messageAction;
    private Player player;

    public ActionLinker(MessageAction messageAction, Player player) {
        this.messageAction = messageAction;
        this.player = player;
    }

    public MessageAction getMessageAction() {
        return messageAction;
    }

    public void setMessageAction(MessageAction messageAction) {
        this.messageAction = messageAction;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
