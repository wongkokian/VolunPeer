import React, { Fragment } from "react";
import useQuizStep from "@/utils/hooks/useQuizStep";
import QuizQuestion from "./QuizQuestion";
import { quiz_stages } from "@/utils/constants/quiz";

function QuizLayout({ increasAboutUsStep }) {
  const { step, increaseStep } = useQuizStep();
  return (
    <Fragment>
      <QuizQuestion
        step={step}
        quizStage={quiz_stages[`quiz_stage${step}`]}
        increaseStep={increaseStep}
      ></QuizQuestion>
    </Fragment>
  );
}

export default QuizLayout;
