"use client";

import React, { Fragment, useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import Interest from "@/app/components/interests/Interest";
import { GET_QUEST_DETAILS, ASSIGN_QUEST_SHIFT } from "@/utils/constants/path";
import styles from "./page.module.scss";
import textStyles from "@/styles/text/text.module.scss";
import flexStyles from "@/styles/flex/flex.module.scss";
import buttonStyles from "@/styles/button/button.module.scss";

function Quest({ params }) {
  const { push } = useRouter();
  const [questDetails, setQuestDetails] = useState();

  const handleShiftCheckboxSelect = (shiftNum) => {
    let shiftList = structuredClone(questDetails.questShifts);
    for (let shift of shiftList) {
      if (shift.shiftNum == shiftNum) {
        shift.isSelected = !shift.isSelected;
      }
    }
    setQuestDetails({ ...questDetails, questShifts: shiftList });
  };

  async function handleRegister() {
    let shiftNums = [];
    let shiftList = structuredClone(questDetails.questShifts);
    for (let shift of shiftList) {
      // If shift is selected, append it
      if (shift.isSelected) {
        shiftNums.push(shift.shiftNum);
      }
    }
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}${ASSIGN_QUEST_SHIFT}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          token: sessionStorage.getItem("token"),
        },
        body: JSON.stringify({
          questId: "QUEST_#" + params.id,
          shiftNums: shiftNums,
        }),
      }
    );
    const data = await response.json();
    if (data.returnCode == 200) {
      push(`/user/dashboard`);
    }
  }

  useEffect(() => {
    async function getQuestDetails() {
      const response = await fetch(
        `${process.env.NEXT_PUBLIC_BACKEND_URL}${GET_QUEST_DETAILS}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            token: sessionStorage.getItem("token"),
          },
          body: JSON.stringify({
            questId: "QUEST_#" + params.id,
          }),
        }
      );
      const data = await response.json();
      if (data.returnCode == 200) {
        setQuestDetails(data);
      }
    }
    getQuestDetails();
  }, []);

  return (
    <Fragment>
      {questDetails && (
        <Fragment>
          {/* HEADER */}
          <div className={styles.quest__header}>
            <div className={styles.quest__headerContent}>
              <div
                className={`${flexStyles.flexCenter}`}
                style={{ gap: "1.5rem" }}
              >
                <span className={`${textStyles.xl} ${textStyles.xxbold}`}>
                  {questDetails.title}
                </span>
                {questDetails.score && (
                  <div
                    className={`${styles.quest__matchIcon} ${textStyles.bold}`}
                  >
                    {questDetails.score}
                  </div>
                )}
              </div>
              <div
                className={`${flexStyles.flexCenter}`}
                style={{ gap: "2rem" }}
              >
                <div className={styles.quest__avatar}></div>
                <div className={styles.quest__headerHosted}>
                  <span
                    className={`${textStyles.md}`}
                    style={{ color: "rgb(100 116 139)" }}
                  >
                    Hosted By
                  </span>
                  <span className={`${textStyles.md} ${textStyles.bold}`}>
                    {questDetails.orgName}
                  </span>
                </div>
              </div>
            </div>
          </div>
          {/* CONTENT */}
          <div
            className={`${styles.quest__contentContainer}`}
            style={{ marginTop: "4rem", marginBottom: "3rem" }}
          >
            <div className={`${styles.quest__content}`}>
              {/* CONTENT LEFT */}
              <div className={`${styles.quest__contentLeft}`}>
                {/* IMAGE */}
                <div
                  className={`${styles.quest__contentLeftImage}`}
                  style={{ backgroundImage: `url(${questDetails.imageUrl})` }}
                ></div>
                {/* DETAILS */}
                <span className={`${textStyles.lg} ${textStyles.bold}`}>
                  Details
                </span>
                <span className={`${textStyles.md}`}>
                  {questDetails.description}
                </span>
                {/* INTERESTS */}
                <Interest text={questDetails.relevantInterest} />
              </div>
              {/* CONTENT RIGHT */}
              <div className={`${styles.quest__contentRight}`}>
                <div className={`${styles.quest__contentRightCard}`}>
                  <div
                    className={`${flexStyles.flexCenter}`}
                    style={{ gap: "2rem" }}
                  >
                    <div
                      className={`${styles.quest__iconCalendar} ${styles.quest__icon}`}
                      style={{ alignSelf: "start" }}
                    ></div>
                    <div
                      className={styles.quest__contentRightCardDetailsContainer}
                      style={{ gap: "1rem" }}
                    >
                      {questDetails.questShifts.map((shift) => {
                        return (
                          <div
                            key={shift.shiftNum}
                            style={{ display: "flex", gap: "2rem" }}
                          >
                            <div
                              className={`${
                                styles.quest__contentRightCardCheckbox
                              } ${
                                shift.isSelected
                                  ? styles.quest__contentRightCardCheckboxSelected
                                  : null
                              }`}
                              style={{ marginTop: "5px" }}
                              onClick={() =>
                                handleShiftCheckboxSelect(shift.shiftNum)
                              }
                            ></div>
                            <div
                              className={`${textStyles.md} ${styles.quest__contentRightCardDetails}`}
                            >
                              {questDetails.questShifts.length > 1 && (
                                <span>Shift {shift.shiftNum}</span>
                              )}
                              <span>
                                {shift.date} {shift.startDateTime} to{" "}
                                {shift.endDateTime}
                              </span>
                            </div>
                          </div>
                        );
                      })}
                    </div>
                  </div>
                  <button
                    className={`${buttonStyles.button} ${buttonStyles.buttonRose}`}
                    style={{
                      paddingTop: "0.5rem",
                      paddingBottom: "0.5rem",
                      marginBottom: "1rem",
                      marginLeft: "7.3rem",
                    }}
                    onClick={handleRegister}
                  >
                    Register
                  </button>
                  <div
                    className={`${flexStyles.flexCenter}`}
                    style={{ gap: "2rem" }}
                  >
                    <div
                      className={`${styles.quest__iconContact} ${styles.quest__icon}`}
                      style={{ alignSelf: "start" }}
                    ></div>
                    <div
                      className={`${textStyles.md} ${styles.quest__contentRightCardDetails}`}
                    >
                      <div>{questDetails.contactName}</div>
                      <div>{questDetails.contactEmail}</div>
                      <div>{questDetails.contactNum}</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </Fragment>
      )}
    </Fragment>
  );
}

export default Quest;
