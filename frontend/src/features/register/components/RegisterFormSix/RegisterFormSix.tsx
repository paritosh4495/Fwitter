import React, { use } from "react";
import { useState } from "react";
import { ValidatedTextInput } from "../../../../components/ValidatedInput/ValidatedTextInput";
import { VisibilityOutlined } from "@mui/icons-material";
import { VisibilityOffOutlined } from "@mui/icons-material";
import { StyledNextButton } from "../RegisterNextButton/RegisterNextButton";
import { useSelector, useDispatch } from "react-redux";
import { RootState, AppDispatch } from "../../../../redux/store";
import { updateRegister, updateUserPassword } from "../../../../redux/Slices/RegisterSlice";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

import "./RegisterFormSix.css";

export const RegisterFormSix: React.FC = () => {

  const navigate = useNavigate();

  const state = useSelector((state: RootState) => state.register);

  const dispatch:AppDispatch = useDispatch();


  const [active, setActive] = useState<boolean>(false);
  const [password, setPassword] = useState<string>("");


  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
    dispatch(updateRegister({ name: "password", value: e.target.value }));
  };

  const toggleView = () => {
    setActive(!active);
  };

  useEffect(() => {
    if(state.login){
      // Store some user info into local storage, so that we can load the user into the use slice 
    // when we hit the feed page 

    navigate("/home");
    }
}, [state.login]);


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

    </div>
  );
};
