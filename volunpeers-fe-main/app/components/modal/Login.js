"use client";

import React, { Fragment, useState } from "react";
import { useRouter } from "next/navigation";
import modalStyles from "@/styles/modal/modal.module.scss";
import textStyles from "@/styles/text/text.module.scss";
import inputStyles from "@/styles/input/input.module.scss";
import buttonStyles from "@/styles/button/button.module.scss";
import { LOG_IN } from "@/utils/constants/path";

function Login({ toggleModal }) {
  const { push } = useRouter();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  async function handleLogIn() {
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}${LOG_IN}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: username,
          password: password,
        }),
      }
    );
    const data = await response.json();
    if (data.returnCode == 200) {
      sessionStorage.setItem("token", data.token);
      push(`/user/dashboard`);
    }
  }

  return (
    <Fragment>
      {/* CROSS */}
      <div className={modalStyles.modal__cross} onClick={toggleModal}></div>
      <span className={`${textStyles.xl} ${textStyles.bold}`}>
        Welcome back, fellow VolunPeer
      </span>
      <span
        className={`${textStyles.md} ${textStyles.bold}`}
        style={{ marginTop: "3rem" }}
      >
        Username
      </span>
      <input
        className={inputStyles.input}
        type="text"
        placeholder="Username"
        value={username}
        style={{ marginTop: "1rem" }}
        onChange={(e) => setUsername(e.target.value)}
      ></input>
      <span
        className={`${textStyles.md} ${textStyles.bold}`}
        style={{ marginTop: "1rem" }}
      >
        Password
      </span>
      <input
        className={inputStyles.input}
        type="text"
        placeholder="Password"
        value={password}
        style={{ marginTop: "1rem" }}
        onChange={(e) => setPassword(e.target.value)}
      ></input>
      <button
        className={`${buttonStyles.button} ${buttonStyles.buttonRose}`}
        style={{ marginTop: "3rem", width: "100%" }}
        onClick={handleLogIn}
      >
        Log in
      </button>
    </Fragment>
  );
}

export default Login;
