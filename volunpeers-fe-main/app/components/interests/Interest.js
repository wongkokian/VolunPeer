import React from "react";
import interestStyles from "@/styles/interest/interest.module.scss";

function Interest({ text }) {
  return <div className={interestStyles.badge}>{text}</div>;
}

export default Interest;
