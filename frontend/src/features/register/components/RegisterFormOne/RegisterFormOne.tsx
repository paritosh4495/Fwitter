import React, { useState, useEffect } from "react";
import { validateName } from "../../../../services/Validators";
import { ValidatedInput } from "../../../../components/ValidatedInput/ValidatedInput";

import { useDispatch, useSelector, UseSelector } from "react-redux";
import { AppDispatch, RootState } from "../../../../redux/store";

import "./RegisterFormOne.css";
import { RegisterDateInput } from "../RegisterDateInput/RegisterDateInput";
import { RegisterNameInputs } from "../RegisterNameInput/RegisterNameInputs";
import { RegisterEmailInput } from "../RegisterEmailInput/RegisterEmailInput";
import { StyledNextButton } from "../RegisterNextButton/RegisterNextButton";
import { incrementStep, updateRegister } from "../../../../redux/Slices/RegisterSlice";


export const RegisterFormOne: React.FC = () => {
  
  const registerState = useSelector((state: RootState) => state.register);
  const dispatch:AppDispatch = useDispatch();


  const nextPage = () => {

    dispatch(updateRegister({
      name : "error",
      value : false
    }));
    dispatch(incrementStep())
  }


  const [buttonActive, setButtonActive] = useState<boolean>(false);

  useEffect(() => {
    if (
      registerState.dobValid &&
      registerState.emailValid &&
      registerState.firstNameValid &&
      registerState.lastNameValid
    ) {
      setButtonActive(true);
    } else {
      setButtonActive(false);
    }
  }, [registerState]);

  return (
    <div className="reg-step-one-container">
      <div className="reg-step-one-content">
        <h1 className="reg-step-one-header">Create your account</h1>
        <RegisterNameInputs firstName={registerState.firstName} lastName={registerState.lastName}/>
        <RegisterEmailInput email={registerState.email} />
        <div className="reg-step-one-dob-disclaimer">
          <p className="reg-step-one-dob-header">Date of Birth</p>
          <span className="reg-step-one-dob-text">This will not be shwon publicly. Confirm your own age, even if this accont is for a buisness, pet, or something else.</span>
        </div>
        <RegisterDateInput date={registerState.dob} />
      </div>
      <StyledNextButton
        disabled={!buttonActive}
        color={"black"}
        active={buttonActive}
        onClick={nextPage}
      >
        NEXT
      </StyledNextButton>
    </div>
  );
};
