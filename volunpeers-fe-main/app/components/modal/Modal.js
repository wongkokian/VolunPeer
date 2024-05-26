import React, { useState } from "react";
import modalStyles from "@/styles/modal/modal.module.scss";

function Modal({ children }) {
  return (
    <div className={modalStyles.modal}>
      <div className={modalStyles.modal__container}>
        <div className={modalStyles.modal__content}>{children}</div>
      </div>
    </div>
  );
}

export default Modal;
