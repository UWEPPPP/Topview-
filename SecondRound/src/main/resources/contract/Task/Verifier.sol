// SPDX-License-Identifier: MIT
pragma solidity ^0.6.0;

contract Verifier{
    address private admin;
    address private logic;
    address private stora;
    mapping(address => bool) private is_register;

    constructor() public{
        admin=msg.sender;
    }

    modifier onlyAdmin(){
        require(msg.sender==admin,"no right");
        _;
    }

    function setLogic(address addr)external onlyAdmin{
        logic=addr;
    }

    function setStor(address addr)external onlyAdmin{
        stora=addr;
    }

    function is_logic(address addr)public view returns (bool) {
        return addr==logic;
    }


    function is_stora(address addr)external view returns (bool) {
        return addr==stora;
    }

    function is_regist(address addr) external view returns (bool){
        return is_register[addr];
    }

    function regist(address addr) external {
        require(is_logic(msg.sender),"no right");
        is_register[addr]=true;
    }


}