import React, { Fragment } from "react";
import { useRouter } from "next/navigation";
import cardStyles from "@/styles/cards/cards.module.scss";
import textStyles from "@/styles/text/text.module.scss";
import flexStyles from "@/styles/flex/flex.module.scss";

function Card({
  type,
  title,
  orgName,
  startDateTime,
  endDateTime,
  location,
  shifts,
  imageUrl,
  numberGoing,
  score,
  questId,
}) {
  const { push } = useRouter();
  const handleCardClick = (questId) => {
    if (!questId) return;
    let id = questId.slice(7);
    push(`/user/explore/quest/${id}`);
  };

  return (
    <div className={cardStyles.card} onClick={() => handleCardClick(questId)}>
      {/* IMAGE */}
      <div
        className={cardStyles.card__image}
        style={{ backgroundImage: `url(${imageUrl})` }}
      >
        {score && (
          <div
            className={`${cardStyles.card__imageMatchIcon} ${textStyles.bold}`}
          >
            {score}
          </div>
        )}
      </div>
      {/* CONTENT */}
      <div className={cardStyles.card__content}>
        {/* TITLE */}
        <span
          className={`${textStyles.lg1} ${textStyles.semibold}`}
          style={{ color: "black" }}
        >
          {title}
        </span>
        {/* ORG NAME */}
        <span className={`${textStyles.sm} ${textStyles.bold}`}>
          Hosted by: {orgName}
        </span>
        <span className={`${textStyles.sm} ${textStyles.bold}`}>
          {type == "upcoming"
            ? "Shifts you've registered:"
            : "Shifts available from:"}
        </span>
        {/* SHIFTS */}
        {type != "upcoming" && (
          <Fragment>
            <div
              className={`${flexStyles.flexCenter}`}
              style={{ gap: "0.5rem", marginLeft: "1rem" }}
            >
              <div
                className={`${cardStyles.card__icon} ${cardStyles.card__iconCalendar}`}
              ></div>
              <span className={`${textStyles.sm}`}>{startDateTime}</span>
            </div>
            <div
              className={`${flexStyles.flexCenter}`}
              style={{ gap: "0.5rem", marginLeft: "1rem" }}
            >
              <div
                className={`${cardStyles.card__icon} ${cardStyles.card__iconCalendar}`}
              ></div>
              <span className={`${textStyles.sm}`}>{endDateTime}</span>
            </div>
          </Fragment>
        )}
        {type == "upcoming" && shifts && (
          <Fragment>
            {shifts.map((shift) => {
              return (
                <div
                  key={shift.startDateTime}
                  className={`${flexStyles.flexCenter}`}
                  style={{ gap: "0.5rem", marginLeft: "1rem" }}
                >
                  <div
                    className={`${cardStyles.card__icon} ${cardStyles.card__iconCalendar}`}
                  ></div>
                  <span className={`${textStyles.sm}`}>
                    {shift.date} {shift.startDateTime}
                  </span>
                </div>
              );
            })}
          </Fragment>
        )}

        {/* {shifts.map((shift) => {
          return (
            <div
              key={shift.startTime}
              className={`${flexStyles.flexCenter}`}
              style={{ gap: "0.5rem", marginLeft: "1rem" }}
            >
              <div
                className={`${cardStyles.card__icon} ${cardStyles.card__iconCalendar}`}
              ></div>
              <span className={`${textStyles.sm}`}>
                {shift.day}, {shift.date} {shift.startTime}
              </span>
            </div>
          );
        })} */}
        {/* NUMBER OF VOLUNTEERS ATTENDING */}
        {numberGoing && (
          <div className={`${flexStyles.flexCenter}`} style={{ gap: "0.5rem" }}>
            <div
              className={`${cardStyles.card__icon} ${cardStyles.card__iconTick}`}
            ></div>
            <span className={`${textStyles.sm}`}>{numberGoing} going</span>
          </div>
        )}
      </div>
    </div>
  );
}

export default Card;
