package interfaces.listeners;

import entity.GameParty;

public interface OnPlayerDisconnectedListener {
    void onPlayerDisconnect(GameParty party);
}
