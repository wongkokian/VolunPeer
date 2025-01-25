import { Fragment } from "react";
import QuizResult from "./QuizResult";
import Navbar from "@/app/components/Navbar";
import styles from "/app/home/about-you/page.module.scss";
export default function Result() {
  return (
    <Fragment>
      <Navbar />
      <div className={styles.aboutyou}>
        <QuizResult />
      </div>
    </Fragment>
  );
}
