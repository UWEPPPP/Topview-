// SPDX-License-Identifier: MIT
pragma solidity ^0.6.0;
pragma experimental ABIEncoderV2;
import "Task/Verifier.sol";

contract NftStorage {
    Verifier private ver;

    struct Token {
        string token_url;
        address owner;
        bool could_sold;
    }

    struct ItemLife {
        uint256 date;
        address owner;
        string status;
    }

    struct Auction {
        uint256 startTime;
        uint256 endTime;
        uint256 highestBid;
        address highestBidder;
    }

    constructor(address veri) public {
        ver = Verifier(veri);
    }

    modifier onlyLogic() {
        require(ver.is_logic(msg.sender), "no right");
        _;
    }

    // Mapping from token ID to account balances
    mapping(uint256 => mapping(address => uint256)) private _balances;

    // Mapping from account to operator approvals
    mapping(address => mapping(address => bool)) private _operatorApprovals;

    mapping(uint256 => Token) private tokens;

    mapping(address => mapping(address => bool)) private paid_connection;

    mapping(uint256 => ItemLife[]) public lifes;

    mapping(uint256 => Auction) public tokenAuction;

    function setAuction(uint256 id, Auction memory auc) external  onlyLogic{
        tokenAuction[id]=auc;
    }

    function getAuction(uint256 id) external view returns(Auction memory){
        return tokenAuction[id];
    }

    function setLifes(uint256 id, ItemLife memory lif) external onlyLogic {
        lifes[id].push(lif);
    }

    function getLifes(uint256 id) external view returns (ItemLife[] memory) {
        return lifes[id];
    }

    function getApproval(address addr, address addr1)
    external
    view
    returns (bool)
    {
        return _operatorApprovals[addr][addr1];
    }

    function setApproval(
        address addr,
        address addr1,
        bool bo
    ) external onlyLogic {
        _operatorApprovals[addr][addr1] = bo;
    }

    function getConnection(address addr, address addr1)
    external
    view
    returns (bool)
    {
        return paid_connection[addr][addr1];
    }

    function setConnection(
        address addr,
        address addr1,
        bool bo
    ) external onlyLogic {
        paid_connection[addr][addr1] = bo;
    }

    function setBalances(
        uint256 id,
        address addr,
        uint256 amount
    ) external onlyLogic {
        _balances[id][addr] = amount;
    }

    function getBalances(uint256 id, address addr)
    external
    view
    returns (uint256)
    {
        return _balances[id][addr];
    }

    function getTokens(uint256 id) external view returns (Token memory) {
        return tokens[id];
    }

    function setTokens(uint256 id, Token memory tok) external onlyLogic {
        tokens[id] = tok;
    }
}
