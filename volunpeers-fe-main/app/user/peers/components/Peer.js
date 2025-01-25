import React, { Fragment } from "react";
import peerStyles from "@/styles/peers/peers.module.scss";
import textStyles from "@/styles/text/text.module.scss";
import buttonStyles from "@/styles/button/button.module.scss";
import { CONNECTION_SEND } from "@/utils/constants/path";

function Peer({ name, quests, peerId }) {
  async function requestPeer() {
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}${CONNECTION_SEND}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          token: sessionStorage.getItem("token"),
        },
        body: JSON.stringify({
          peerId: peerId,
        }),
      }
    );
    const data = await response.json();
    console.log(data);
  }
  return (
    <div className={peerStyles.peer}>
      <div style={{ display: "flex", alignItems: "center", gap: "2rem" }}>
        <div className={peerStyles.peerAvatar}></div>
        <span className={`${textStyles.lg} ${textStyles.semibold}`}>
          {name}
        </span>
        {quests && (
          <div
            style={{
              width: "300px",
              display: "flex",
              flexDirection: "column",
              gap: "1rem",
            }}
          >
            {quests.map((quest) => {
              return (
                <span key={quest} className={peerStyles.peerQuest}>
                  {quest}
                </span>
              );
            })}
          </div>
        )}
      </div>
      {quests && (
        <button
          className={`${buttonStyles.button} ${buttonStyles.buttonRose}`}
          onClick={() => requestPeer()}
        >
          Connect
        </button>
      )}
    </div>
  );
}

export default Peer;
