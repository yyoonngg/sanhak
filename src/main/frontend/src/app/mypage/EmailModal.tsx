import React, { useState } from 'react';

type EmailModalProps = {
    isOpen: boolean;
    onClose: () => void;
    onSend: (contents: string) => void;
};

const EmailModal: React.FC<EmailModalProps> = ({ isOpen, onClose, onSend }) => {
    const [contents, setContents] = useState('');

    if (!isOpen) return null;

    const handleSend = () => {
        if (contents) {
            onSend( contents);
            setContents("");
        } else {
            alert("Recipient와 내용은 필수입니다.");
        }
    };

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50">
            <div className="bg-white p-5 rounded-lg shadow-md w-1/3">
                <div className="flex justify-between items-center mb-4">
                    <h2 className="text-lg font-bold">Send Email</h2>
                    <button onClick={onClose}>
                        <span>×</span>
                    </button>
                </div>
                <div className="mb-3">
                    <label className="block text-sm font-medium mb-1">Message</label>
                    <textarea
                        value={contents}
                        onChange={(e) => setContents(e.target.value)}
                        className="w-full p-2 border rounded-lg"
                        rows={5}
                        placeholder="Enter your message here..."
                    />
                </div>
                <div className="flex justify-end space-x-2">
                    <button
                        onClick={onClose}
                        className="px-4 py-2 bg-gray-300 rounded-lg"
                    >
                        Cancel
                    </button>
                    <button
                        onClick={handleSend}
                        className="px-4 py-2 bg-blue-500 rounded-lg"
                    >
                        Send
                    </button>
                </div>
            </div>
        </div>
    );
};

export default EmailModal;