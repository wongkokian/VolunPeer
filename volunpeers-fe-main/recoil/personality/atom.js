import { atom } from "recoil";

export const personalityState = atom({
  key: "personalityState",
  default: {
    first: "",
    second: "",
    third: "",
    fourth: "",
  },
});
