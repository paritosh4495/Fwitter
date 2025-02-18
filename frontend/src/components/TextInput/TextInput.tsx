import React, { useState } from 'react';

interface TextInputProps {
  name: string;
  label: string;
  errorMessage: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  maxLength?: number;
  validator?: (value: string) => boolean;
}

export const TextInput: React.FC<TextInputProps> = ({
  name,
  label,
  errorMessage,
  onChange,
  maxLength,
  validator,
}) => {
  const [inputValue, setInputValue] = useState<string>('');

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    if (maxLength && value.length > maxLength) return;
    if (validator && !validator(value)) return;
    setInputValue(value);
    onChange(e);
  };

  return (
    <div className="text-input">
      <div>
        <span>{label}</span>
        <input
          name={name}
          value={inputValue}
          onChange={handleInputChange}
        />
      </div>
    </div>
  );
};
