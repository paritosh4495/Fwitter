import React from 'react'
import { Checkbox } from '../../../../components/Checkbox/Checkbox'
import { DropDown } from '../../../../components/DropDown/DropDown'
import { ValidatedTextInput } from '../../../../components/ValidatedInput/ValidatedTextInput'
import { countryCodeDropDown } from '../../utils/RegisterModalUtils'
import { useEffect, useState } from 'react'
import { validatePhone } from '../../../../services/Validators'
import { StyledNextButton } from '../RegisterNextButton/RegisterNextButton'
import { useDispatch, useSelector } from 'react-redux'
import { AppDispatch, RootState } from '../../../../redux/store'
import { updateUserPhone, updateRegister } from '../../../../redux/Slices/RegisterSlice'
import './RegisterFormFour.css'


export const RegisterFormFour:React.FC = () => {

    const state = useSelector((state:RootState) => state.register)

    const [phoneCode, setPhoneCode] = useState<string>("+1")
    const [phoneNumber, setPhoneNumber] = useState<string>("")
    const [validNumber, setValidNumber] = useState<boolean>(true)


    const dispatch:AppDispatch = useDispatch();


    const changeCode = (e:React.ChangeEvent<HTMLSelectElement>) => {
        setPhoneCode(e.target.value.split(" ")[0]);
    }

    const changePhoneNumber = (e:React.ChangeEvent<HTMLInputElement>) => {
        setPhoneNumber(e.target.value);

        dispatch(updateRegister({
            name: "phoneNumber",
            value: e.target.value
        }));
    }

    const sendPhoneNumber = () => {

        dispatch(updateUserPhone({
            username: state.username,
            phone: phoneNumber
        }))
    }


    useEffect(() => {
        console.log(phoneCode, phoneNumber)
        if(phoneNumber){
            setValidNumber(validatePhone(phoneNumber));
        }
        console.log(state)
    }, [phoneCode, phoneNumber])

  return (
    <div className='reg-step-four-container'>

        <div className='reg-step-four-content'>

            <h1>Add a phone Number</h1>
            <p className='reg-step-four-subhead'>Enter the phone number you would like to associate with your Fwitter account. You wont get a verification code sent here.</p>
            <div className='reg-step-four-inputs'>
                <DropDown 
                
                    content={countryCodeDropDown}
                    change={changeCode}    
                    label={"Country Code"}
                    defaultValue={"US +1"} 
                    />
                    <ValidatedTextInput 
                    valid = {true}
                    name={'phoneNumber'}
                    label={"Your Phone Number"}
                    changeValue={changePhoneNumber}
                    />
                    {validNumber ? <></> : <p className='reg-step-four-invalid'>Please Enter a valid 10 digit number</p>}
            </div>
            <div className='reg-step-four-check-group'>
                <p>Let People who have your phone number find and connect with you on Fwitter. <span className='reg-step-four-link'>Learn More</span>.</p>
                <Checkbox/>
            </div>
            <div className='reg-step-four-check-group'>
                <p>Let Fwitter use your phone number to personalize our servives, including ads (if permitted by your Ads preferences). If you dont enable this, Fwitter will still use your phone number for purposes including account security, spam, fraud and abuse preventional <span className='reg-step-four-link'>See our Privacy Policy for more information.</span>.</p>
                <Checkbox/>
            </div>

        </div>
        <StyledNextButton disabled={(phoneNumber && validNumber) ? false : true} color={'black'} active={(phoneNumber && validNumber) ?  true : false} onClick={sendPhoneNumber} >
                Update Number
            </StyledNextButton>
    </div>
  )
}
