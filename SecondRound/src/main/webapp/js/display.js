$(function() {
    const nftContainer = $('#nft');

    function renderNFT(nftData) {
        // 清空容器
        nftContainer.html('');
        // 遍历NFT数据
        $.each(nftData, function(index, item) {
            const nftItem = $('<div>').addClass('nft-item');

            const nftImage = $('<img>').attr('src', 'https://ipfs.io/ipfs/' + item.ipfsCid).attr('alt', item.name);

            const nftInfo = $('<div>').addClass('nft-info');

            const nftName = $('<h2>').text(item.name);

            const nftPrice = $('<p>').text('价格:' + item.price);

            const nftType = $('<p>').text('类型:' + item.type);

            const nftOwner = $('<p>').text('所有者:' + item.owner);

            const nftDescription = $('<p>').text('描述:' + item.description);

            nftInfo.append(nftName, nftPrice, nftType, nftOwner, nftDescription);
            nftItem.append(nftImage, nftInfo);
            nftContainer.append(nftItem);
        });
    }

    $.get('/nft', function(data) {
        renderNFT(data);
    });
});