import React, { Fragment } from "react";
import textStyles from "@/styles/text/text.module.scss";
import buttonStyles from "@/styles/button/button.module.scss";
import styles from "../page.module.scss";
import characterStyles from "@/styles/characters/characters.module.scss";
import flexStyles from "@/styles/flex/flex.module.scss";

function QuizPreview({ increaseStep }) {
  return (
    <Fragment>
      <span className={`${textStyles.xl} ${textStyles.bold}`}>
        Discover your volunteering persona
      </span>
      <span
        className={`${textStyles.md} ${textStyles.light}`}
        style={{ marginTop: "1.5rem" }}
      >
        Quests will be recommended based on your assigned volunteering persona
      </span>
      <div
        className={flexStyles.flexCenter}
        style={{ marginTop: "4rem", gap: "4rem" }}
      >
        <div
          className={`${characterStyles.character} ${characterStyles.character__archer} ${styles.aboutyou__character}`}
        ></div>
        <div
          className={`${characterStyles.character} ${characterStyles.character__musketeer} ${styles.aboutyou__character}`}
        ></div>
        <div
          className={`${characterStyles.character} ${characterStyles.character__wizard} ${styles.aboutyou__character}`}
        ></div>
      </div>
      <div className={styles.aboutyou__buttonContainer}>
        <button
          className={`${buttonStyles.buttonRose} ${buttonStyles.button}`}
          style={{ width: "16rem" }}
          onClick={increaseStep}
        >
          Start Personality Quiz
        </button>
      </div>
    </Fragment>
  );
}

export default QuizPreview;
