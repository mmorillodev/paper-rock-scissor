package interfaces.listeners;

import entity.PlayerImpl;

public interface OnMessageSentListener {
    void onMessageSent(PlayerImpl player, String message);
}
