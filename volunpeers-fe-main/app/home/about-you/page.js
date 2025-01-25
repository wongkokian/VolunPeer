"use client";

import Navbar from "@/app/components/Navbar";
import React, { Fragment } from "react";
import styles from "./page.module.scss";
import Interests from "./components/Interests";
import QuizPreview from "./components/QuizPreview";
import useAboutYouStep from "@/utils/hooks/useAboutYouStep";
import QuizLayout from "@/app/components/quiz/QuizLayout";

function AboutYou() {
  const { step, increaseStep } = useAboutYouStep();

  return (
    <Fragment>
      <Navbar />
      <div className={styles.aboutyou}>
        {step == 1 && <Interests increaseStep={increaseStep} />}
        {step == 2 && <QuizPreview increaseStep={increaseStep} />}
        {step == 3 && <QuizLayout increasAboutUsStep={increaseStep} />}
      </div>
    </Fragment>
  );
}

export default AboutYou;
