// SPDX-License-Identifier: MIT
// OpenZeppelin Contracts (last updated v4.8.0) (token/ERC1155/ERC1155.sol)

pragma solidity ^0.6.1;
pragma experimental ABIEncoderV2;
import "./IERC1155.sol";
import "./IERC1155Receiver.sol";
import "./IERC1155MetadataURI.sol";
import "./Address.sol";
import "./Context.sol";
import "./ERC165.sol";
import "Task/NftStorage.sol";
import "Task/Verifier.sol";

contract MyToken is Context, ERC165, IERC1155, IERC1155MetadataURI {
    using Address for address;
    NftStorage private nstorage;
    Verifier private verifier;
    address public _implementation; // 逻辑合约地址。implementation合约同一个位置的状态变量类型必须和Proxy合约的相同，不然会报错。
    uint256 private tokenId = 1;
    address private admin;

    constructor(address sto, address ver) public {
        nstorage = NftStorage(sto);
        verifier = Verifier(ver);
        admin = _msgSender();
        nstorage.setBalances(0, admin, 2000);
    }

    modifier onlyFresh() {
        require(verifier.is_regist(_msgSender()) != true, "Have regist!");
        _;
    }

    function regiter() external onlyFresh {
        _mint(_msgSender(), 0, 1000, "");
        setApprovalForAll(admin, true);
    }

    function getBalance() external view returns (uint256) {
        return balanceOf(_msgSender(), 0);
    }

    function issueNft(string memory token_url) external returns (uint256) {
        uint256 id = tokenId;
        _mint(_msgSender(), tokenId, 1, "");
        _setURI(tokenId, token_url, _msgSender());
        nstorage.setLifes(
            id,
            NftStorage.ItemLife(now, _msgSender(), "initial mint")
        );
        tokenId++;
        return id;
    }

    function buyNft(uint256 id, uint256 amount) external {
        NftStorage.Token memory tok = nstorage.getTokens(id);
        safeTransferFrom(_msgSender(), tok.owner, 0, amount, "");
        safeTransferFrom(tok.owner, _msgSender(), id, 1, "");
        tok.owner = _msgSender();
        nstorage.setLifes(
            id,
            NftStorage.ItemLife(now, _msgSender(), "traded being a item ")
        );
        nstorage.setTokens(id, tok);
    }

    function tranferNft(uint256 id, address to) external {
        NftStorage.Token memory tok = nstorage.getTokens(id);
        safeTransferFrom(_msgSender(), to, id, 1, "");
        tok.owner = to;
        nstorage.setLifes(
            id,
            NftStorage.ItemLife(now, _msgSender(), "sended being a gift ")
        );
        nstorage.setTokens(id, tok);
    }

    function upOrDown(uint256 id, bool choice) external {
        NftStorage.Token memory tok = nstorage.getTokens(id);
        tok.could_sold = choice;
        nstorage.setTokens(id, tok);
    }

    function auctionBegin(uint256 id) external {
        require(nstorage.getTokens(id).owner == _msgSender(), "no right");

    }

    function getNftLife(uint256 id)
    external
    view
    returns (NftStorage.ItemLife[] memory)
    {
        return nstorage.getLifes(id);
    }

    /**
     * 以下为openzeppelin提供的ERC1155合约
     */

    function supportsInterface(bytes4 interfaceId)
    public
    view
    virtual
    override(ERC165, IERC165)
    returns (bool)
    {
        return
        interfaceId == type(IERC1155).interfaceId ||
        interfaceId == type(IERC1155MetadataURI).interfaceId ||
        super.supportsInterface(interfaceId);
    }

    function uri(uint256 id)
    public
    view
    virtual
    override
    returns (string memory)
    {
        string memory uri_ = nstorage.getTokens(id).token_url;
        return uri_;
    }

    function balanceOf(address account, uint256 id)
    public
    view
    virtual
    override
    returns (uint256)
    {
        require(
            account != address(0),
            "ERC1155: address zero is not a valid owner"
        );
        return nstorage.getBalances(id, account);
    }

    function balanceOfBatch(address[] memory accounts, uint256[] memory ids)
    public
    view
    virtual
    override
    returns (uint256[] memory)
    {
        require(
            accounts.length == ids.length,
            "ERC1155: accounts and ids length mismatch"
        );

        uint256[] memory batchBalances = new uint256[](accounts.length);

        for (uint256 i = 0; i < accounts.length; ++i) {
            batchBalances[i] = balanceOf(accounts[i], ids[i]);
        }

        return batchBalances;
    }

    function setApprovalForAll(address operator, bool approved)
    public
    virtual
    override
    {
        _setApprovalForAll(_msgSender(), operator, approved);
    }

    function isApprovedForAll(address account, address operator)
    public
    view
    virtual
    override
    returns (bool)
    {
        return nstorage.getApproval(account, operator);
    }

    function safeTransferFrom(
        address from,
        address to,
        uint256 id,
        uint256 amount,
        bytes memory data
    ) public virtual override {
        require(
            from == _msgSender() ||
            isApprovedForAll(from, _msgSender()) ||
            nstorage.getConnection(to, from),
            "ERC1155: caller is not token owner or approved"
        );
        if (id == 0) {
            nstorage.setConnection(from, to, true);
        } else {
            nstorage.setConnection(to, from, false);
        }
        _safeTransferFrom(from, to, id, amount, data);
    }

    function safeBatchTransferFrom(
        address from,
        address to,
        uint256[] memory ids,
        uint256[] memory amounts,
        bytes memory data
    ) public virtual override {
        require(
            from == _msgSender() || isApprovedForAll(from, admin),
            "ERC1155: caller is not token owner or approved"
        );
        _safeBatchTransferFrom(from, to, ids, amounts, data);
    }

    function _safeTransferFrom(
        address from,
        address to,
        uint256 id,
        uint256 amount,
        bytes memory data
    ) internal virtual {
        require(to != address(0), "ERC1155: transfer to the zero address");

        address operator = _msgSender();
        uint256[] memory ids = _asSingletonArray(id);
        uint256[] memory amounts = _asSingletonArray(amount);

        _beforeTokenTransfer(operator, from, to, ids, amounts, data);

        uint256 fromBalance = nstorage.getBalances(id, from);
        require(
            fromBalance >= amount,
            "ERC1155: insufficient balance for transfer"
        );
        nstorage.setBalances(id, from, fromBalance - amount);
        uint256 yet = nstorage.getBalances(id, to);
        nstorage.setBalances(id, to, yet + amount);
        emit TransferSingle(operator, from, to, id, amount);

        _afterTokenTransfer(operator, from, to, ids, amounts, data);

        _doSafeTransferAcceptanceCheck(operator, from, to, id, amount, data);
    }

    function _safeBatchTransferFrom(
        address from,
        address to,
        uint256[] memory ids,
        uint256[] memory amounts,
        bytes memory data
    ) internal virtual {
        require(
            ids.length == amounts.length,
            "ERC1155: ids and amounts length mismatch"
        );
        require(to != address(0), "ERC1155: transfer to the zero address");

        address operator = _msgSender();

        _beforeTokenTransfer(operator, from, to, ids, amounts, data);

        for (uint256 i = 0; i < ids.length; ++i) {
            uint256 id = ids[i];
            uint256 amount = amounts[i];

            uint256 fromBalance = nstorage.getBalances(id, from);
            require(
                fromBalance >= amount,
                "ERC1155: insufficient balance for transfer"
            );

            nstorage.setBalances(id, from, fromBalance - amount);

            uint256 yet = nstorage.getBalances(id, to);
            nstorage.setBalances(id, to, yet + amount);
        }

        emit TransferBatch(operator, from, to, ids, amounts);

        _afterTokenTransfer(operator, from, to, ids, amounts, data);

        _doSafeBatchTransferAcceptanceCheck(
            operator,
            from,
            to,
            ids,
            amounts,
            data
        );
    }

    function _setURI(
        uint256 id,
        string memory newuri,
        address owner
    ) internal virtual {
        nstorage.setTokens(id, NftStorage.Token(newuri, owner, false));
    }

    function _mint(
        address to,
        uint256 id,
        uint256 amount,
        bytes memory data
    ) internal virtual {
        require(to != address(0), "ERC1155: mint to the zero address");
        address operator = admin;
        uint256 yet = nstorage.getBalances(id, operator);
        nstorage.setBalances(id, operator, yet + amount);
        uint256[] memory ids = _asSingletonArray(id);
        uint256[] memory amounts = _asSingletonArray(amount);

        _beforeTokenTransfer(operator, address(0), to, ids, amounts, data);

        uint256 yet1 = nstorage.getBalances(id, to);
        nstorage.setBalances(id, to, yet1 + amount);
        emit TransferSingle(operator, address(0), to, id, amount);

        _afterTokenTransfer(operator, address(0), to, ids, amounts, data);

        _doSafeTransferAcceptanceCheck(
            operator,
            address(0),
            to,
            id,
            amount,
            data
        );
    }

    function _mintBatch(
        address to,
        uint256[] memory ids,
        uint256[] memory amounts,
        bytes memory data
    ) internal virtual {
        require(to != address(0), "ERC1155: mint to the zero address");
        require(
            ids.length == amounts.length,
            "ERC1155: ids and amounts length mismatch"
        );

        address operator = _msgSender();

        _beforeTokenTransfer(operator, address(0), to, ids, amounts, data);

        for (uint256 i = 0; i < ids.length; i++) {
            uint256 yet = nstorage.getBalances(ids[i], to);
            nstorage.setBalances(ids[i], to, yet + amounts[i]);
        }

        emit TransferBatch(operator, address(0), to, ids, amounts);

        _afterTokenTransfer(operator, address(0), to, ids, amounts, data);

        _doSafeBatchTransferAcceptanceCheck(
            operator,
            address(0),
            to,
            ids,
            amounts,
            data
        );
    }

    function _burn(
        address from,
        uint256 id,
        uint256 amount
    ) internal virtual {
        require(from != address(0), "ERC1155: burn from the zero address");

        address operator = _msgSender();
        uint256[] memory ids = _asSingletonArray(id);
        uint256[] memory amounts = _asSingletonArray(amount);

        _beforeTokenTransfer(operator, from, address(0), ids, amounts, "");

        uint256 fromBalance = nstorage.getBalances(id, from);
        require(fromBalance >= amount, "ERC1155: burn amount exceeds balance");

        nstorage.setBalances(id, from, fromBalance - amount);

        emit TransferSingle(operator, from, address(0), id, amount);

        _afterTokenTransfer(operator, from, address(0), ids, amounts, "");
    }

    function _burnBatch(
        address from,
        uint256[] memory ids,
        uint256[] memory amounts
    ) internal virtual {
        require(from != address(0), "ERC1155: burn from the zero address");
        require(
            ids.length == amounts.length,
            "ERC1155: ids and amounts length mismatch"
        );

        address operator = _msgSender();

        _beforeTokenTransfer(operator, from, address(0), ids, amounts, "");

        for (uint256 i = 0; i < ids.length; i++) {
            uint256 id = ids[i];
            uint256 amount = amounts[i];

            uint256 fromBalance = nstorage.getBalances(id, from);
            require(
                fromBalance >= amount,
                "ERC1155: burn amount exceeds balance"
            );

            nstorage.setBalances(id, from, fromBalance - amount);
        }

        emit TransferBatch(operator, from, address(0), ids, amounts);

        _afterTokenTransfer(operator, from, address(0), ids, amounts, "");
    }

    function _setApprovalForAll(
        address owner,
        address operator,
        bool approved
    ) internal virtual {
        require(owner != operator, "ERC1155: setting approval status for self");
        nstorage.setApproval(owner, operator, approved);
        emit ApprovalForAll(owner, operator, approved);
    }

    function _beforeTokenTransfer(
        address operator,
        address from,
        address to,
        uint256[] memory ids,
        uint256[] memory amounts,
        bytes memory data
    ) internal virtual {}

    function _afterTokenTransfer(
        address operator,
        address from,
        address to,
        uint256[] memory ids,
        uint256[] memory amounts,
        bytes memory data
    ) internal virtual {}

    function _doSafeTransferAcceptanceCheck(
        address operator,
        address from,
        address to,
        uint256 id,
        uint256 amount,
        bytes memory data
    ) private {
        if (to.isContract()) {
            try
            IERC1155Receiver(to).onERC1155Received(
                operator,
                from,
                id,
                amount,
                data
            )
            returns (bytes4 response) {
                if (response != IERC1155Receiver.onERC1155Received.selector) {
                    revert("ERC1155: ERC1155Receiver rejected tokens");
                }
            } catch Error(string memory reason) {
                revert(reason);
            } catch {
                revert("ERC1155: transfer to non-ERC1155Receiver implementer");
            }
        }
    }

    function _doSafeBatchTransferAcceptanceCheck(
        address operator,
        address from,
        address to,
        uint256[] memory ids,
        uint256[] memory amounts,
        bytes memory data
    ) private {
        if (to.isContract()) {
            try
            IERC1155Receiver(to).onERC1155BatchReceived(
                operator,
                from,
                ids,
                amounts,
                data
            )
            returns (bytes4 response) {
                if (
                    response != IERC1155Receiver.onERC1155BatchReceived.selector
                ) {
                    revert("ERC1155: ERC1155Receiver rejected tokens");
                }
            } catch Error(string memory reason) {
                revert(reason);
            } catch {
                revert("ERC1155: transfer to non-ERC1155Receiver implementer");
            }
        }
    }

    function _asSingletonArray(uint256 element)
    private
    pure
    returns (uint256[] memory)
    {
        uint256[] memory array = new uint256[](1);
        array[0] = element;

        return array;
    }
}
