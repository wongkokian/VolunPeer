"use client";

import React, { Fragment, useState } from "react";
import { useRouter } from "next/navigation";
import modalStyles from "@/styles/modal/modal.module.scss";
import textStyles from "@/styles/text/text.module.scss";
import inputStyles from "@/styles/input/input.module.scss";
import buttonStyles from "@/styles/button/button.module.scss";
import { SIGN_UP, LOG_IN } from "@/utils/constants/path";
import { SearchBox } from "@mapbox/search-js-react";

function SignUp({ toggleModal }) {
  const { push } = useRouter();

  const [name, setName] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [location, setLocation] = useState("");
  const [locationName, setLocationName] = useState("");

  // For location and address search box
  const [searchBoxAddressDisplay, setSearchBoxAddressDisplay] = useState("");
  const [hasEnteredAddress, setHasEnteredAddress] = useState(false);

  async function handleSignUp() {
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_BACKEND_URL}${SIGN_UP}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: username,
          password: password,
          name: name,
          locationCoordinates: location,
          locationName: locationName,
        }),
      }
    );
    const data = await response.json();
    // If sign up is successful, call login to get token to store in session storage
    if (data.returnCode == 200) {
      const loginResponse = await fetch(
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
      const loginData = await loginResponse.json();
      // If login is successful, store response token in session storage
      if (loginData.returnCode == 200) {
        sessionStorage.setItem("token", loginData.token);
        // Route to /home/about-you
        push("/home/about-you");
      }
    }
  }

  return (
    <Fragment>
      {/* CROSS */}
      <div className={modalStyles.modal__cross} onClick={toggleModal}></div>
      <span className={`${textStyles.xl} ${textStyles.bold}`}>
        Finish signing up
      </span>
      <span
        className={`${textStyles.md} ${textStyles.bold}`}
        style={{ marginTop: "3rem" }}
      >
        Your name
      </span>
      <input
        className={inputStyles.input}
        type="text"
        placeholder="Name"
        value={name}
        style={{ marginTop: "1rem" }}
        onChange={(e) => setName(e.target.value)}
      ></input>
      <span
        className={`${textStyles.md} ${textStyles.bold}`}
        style={{ marginTop: "1rem" }}
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
      <span
        className={`${textStyles.md} ${textStyles.bold}`}
        style={{ marginTop: "1rem" }}
      >
        Location
      </span>
      <div className={inputStyles.input} style={{ marginTop: "1rem" }}>
        <SearchBox
          accessToken={process.env.NEXT_PUBLIC_MAPBOX_ACCESS_TOKEN}
          // theme={{
          //   variables: {
          //     fontFamily: "Poppins",
          //     fontWeight: "400",
          //     unit: "16px",
          //     borderRadius: "4px 4px 0 0",
          //     boxShadow: "0 0 0 1px silver",
          //   },
          // }}
          placeholder="Enter a location"
          value={searchBoxAddressDisplay}
          onChange={(address) => {
            if (!hasEnteredAddress) {
              // If user has entered an address but location field not set yet it means they hasnt select a suggestion.
              // In this case we should invalidate their location field
              setHasEnteredAddress(true);
            }
          }}
          onRetrieve={(results) => {
            var name = results.features[0].properties.name;
            // var address = results.features[0].properties.address;
            // if (address !== undefined) {
            //   name = name + " @ " + address; // Storing a more detailed address
            //   name = name.replace('"', ""); // Remove any unwanted characters
            // } else {
            //   // If place is a town or city, this would be  the more detailed address
            //   name =
            //     name +
            //     ", " +
            //     results.features[0].properties.place_formatted;
            // }
            var locationString =
              results.features[0].geometry.coordinates[0] +
              "," +
              results.features[0].geometry.coordinates[1];
            console.log(
              "Name and Address of selected: " +
                name +
                " with Coordinates: " +
                locationString
            );

            setSearchBoxAddressDisplay(name); // Update search box address display to reflect the selected address
            setLocation(locationString);
            setLocationName(name);
          }}
          onSuggest={(results) => {}}
        />
      </div>
      <span style={{ fontSize: "12px", marginTop: "1px" }}>
        Your location will help us find the nearest quests for you
      </span>
      <button
        className={`${buttonStyles.button} ${buttonStyles.buttonRose}`}
        style={{ marginTop: "3rem", width: "100%" }}
        onClick={handleSignUp}
      >
        Sign up
      </button>
    </Fragment>
  );
}

export default SignUp;
