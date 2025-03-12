import React from "react";
import { useState } from "react";
import { ValidatedTextInput } from "../../../../components/ValidatedInput/ValidatedTextInput";
import { VisibilityOutlined } from "@mui/icons-material";
import { VisibilityOffOutlined } from "@mui/icons-material";

import { useSelector, useDispatch } from "react-redux";
import { RootState, AppDispatch } from "../../../../redux/store";
import { updateRegister } from "../../../../redux/Slices/RegisterSlice";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

import "./RegisterForm.css"
import "../../../../assets/global.css"

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
    <div className="register-container">
      <div className="register-content">
        <h1 className="register-header-2">You'll need a password</h1>
        <p className="register-text color-gray">Make Sure It's 8 Characters or more.</p>
        <div className="register-six-password">
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
          <div onClick={toggleView} className="register-six-icon">
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
