import { useState } from "react";

const useModal = () => {
  const [openModal, setOpenModal] = useState(false);
  const toggleModal = () => {
    setOpenModal(!openModal);
  };

  return { openModal, toggleModal };
};

export default useModal;
