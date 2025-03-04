import React, { use } from "react";
import { useState } from "react";
import { ValidatedTextInput } from "../../../../components/ValidatedInput/ValidatedTextInput";
import { VisibilityOutlined } from "@mui/icons-material";
import { VisibilityOffOutlined } from "@mui/icons-material";
import { StyledNextButton } from "../RegisterNextButton/RegisterNextButton";
import { useSelector, useDispatch } from "react-redux";
import { RootState, AppDispatch } from "../../../../redux/store";
import { updateUserPassword } from "../../../../redux/Slices/RegisterSlice";

import "./RegisterFormSix.css";

export const RegisterFormSix: React.FC = () => {


  const state = useSelector((state: RootState) => state.register);

  const dispatch:AppDispatch = useDispatch();


  const [active, setActive] = useState<boolean>(false);
  const [password, setPassword] = useState<string>("");


  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const toggleView = () => {
    setActive(!active);
  };


  const sendPassword = () => {
    dispatch(updateUserPassword({
      username: state.username,
      password
    }));
  };  

  return (
    <div className="reg-step-six-container">
      <div className="reg-step-six-content">
        <h1>You'll need a password</h1>
        <p>Make Sure It's 8 Characters or more.</p>
        <div className="reg-step-six-password">
          <ValidatedTextInput
            valid={true}
            label={"Password"}
            name={"password"}
            changeValue={handleChange}
            attributes={{
              minLength: 8,
              type: active ? "text" : "password",
            }}
          />
          <div onClick={toggleView} className="reg-step-six-icon">
            {active ? (
              <VisibilityOffOutlined
                sx={{
                  fontSize: "24px",
                }}
              />
            ) : (
              <VisibilityOutlined
                sx={{
                  fontSize: "24px",
                }}
              />
            )}
          </div>
        </div>
      </div>
      <StyledNextButton active={password.length >= 8} disabled={!(password.length >= 8)} onClick={sendPassword} color={"black"}>
        Next
      </StyledNextButton>
    </div>
  );
};
