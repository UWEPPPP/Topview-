// SPDX-License-Identifier: MIT
pragma solidity ^0.6.0;
pragma experimental ABIEncoderV2;
import "Task/ERC1155.sol";

contract NftMarket is ERC1155 {
    uint256 private tokenId=1;
    address private admin;
    constructor () ERC1155() public{
        admin = _msgSender();
    }

    function regiter() external {
        _mint(_msgSender(),0,1000,"");
        setApprovalForAll(admin,true);
    }

    function getBalance() external view returns (uint256){
        return  balanceOf(_msgSender(),0);
    }

    function issueNft(string memory token_url ) external {
        _mint(_msgSender(),tokenId,1,"");
        _setURI(tokenId,token_url,_msgSender());
        tokenId++;
    }

    function buyNft(uint256 id,uint256 amount) external   returns (string memory ) {
        Token memory tok = getTokenDetail(id);
        safeTransferFrom(_msgSender(),tok.owner,0,amount,"");
        safeTransferFrom(tok.owner,_msgSender(),id,1,"");
        return tok.token_url;
    }

}

// contract NftStorage {

//  //   mapping(address => )
// }