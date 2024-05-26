import React, { Fragment, useEffect } from "react";
import cardsStyles from "@/styles/cards/cards.module.scss";
import Card from "./Card";

function Cards({ type, quests }) {
  return (
    <Fragment>
      {quests && (
        <div className={cardsStyles.cards}>
          {quests.map((quest) => {
            return (
              <Card
                key={quest.questId}
                type={type}
                imageUrl={quest.imageUrl}
                title={quest.title}
                orgName={quest.orgName}
                location={quest.locationName}
                startDateTime={quest.startDateTime}
                endDateTime={quest.endDateTime}
                score={quest.score ? quest.score : null}
                numberGoing={quest.numRegistered}
                questId={quest.questId}
                shifts={quest.shifts}
                onClick={() => handleCardClick(quest.questId)}
              ></Card>
            );
          })}
        </div>
      )}
    </Fragment>
  );
}

export default Cards;
