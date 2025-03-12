import React, { useState } from "react";
import { useDispatch, UseDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../../../redux/store";
import { decrementStep } from "../../../../redux/Slices/RegisterSlice";
import { Modal } from "../../../../components/Modal/Modal";
import "./RegisterModal.css";
import { RegistrationStepCounter } from "../RegistrationStepCounter/RegistrationStepCounter";
import { determineModalContent } from "../../utils/RegisterModalUtils";
import { RegisterNextButton, StyledNextButton } from "../RegisterNextButton/RegisterNextButton";

export const RegisterModal: React.FC = () => {
  const state = useSelector((state: RootState) => state.register);
  const dispatch: AppDispatch = useDispatch();

  const stepButtonClicked = () => {
    dispatch(decrementStep());
  };

  return (
    <Modal
      topContent={<RegistrationStepCounter step={state.step} changeStep={stepButtonClicked}/>}
      content={determineModalContent(state.step)}
      bottomContent={<RegisterNextButton step={state.step} />}
      />
    )
};
