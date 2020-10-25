package interfaces;

import entity.PlayerImpl;

public interface OnMessageSentListener {
    void onMessageSent(PlayerImpl player, String message);
}
