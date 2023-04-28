// 模拟藏品数据
const collectionData = [
    {
        id: 1,
        name: '藏品1',
        image: 'https://via.placeholder.com/200x150',
        caster: '铸造者1',
        date: '2023-04-28',
        price: 100
    },
    {
        id: 2,
        name: '藏品2',
        image: 'https://ipfs.io/ipfs/Qmdm3VcsXioGcVfyqPWG8ts9eLztmvSJn7BhMwh7jdzNAz',
        caster: '铸造者2',
        date: '2023-04-27',
        price: 200
    },
    {
        id: 3,
        name: '藏品3',
        image: 'https://via.placeholder.com/200x150',
        caster: '铸造者3',
        date: '2023-04-26',
        price: 300
    }
];

// 获取展示藏品的容器
const collectionContainer = document.querySelector('#collection');

// 渲染藏品列表
function renderCollection() {
    // 清空容器
    collectionContainer.innerHTML = '';
    // 遍历藏品数据
    collectionData.forEach(item => {
        // 创建藏品元素
        const collectionItem = document.createElement('div');

        // 添加样式类
        collectionItem.classList.add('collection-item');

        // 创建藏品图片元素
        const collectionImage = document.createElement('img');
        collectionImage.src = item.image;
        collectionImage.alt = item.name;

        // 创建藏品信息元素
        const collectionInfo = document.createElement('div');
        collectionInfo.classList.add('collection-info');

        // 创建藏品名称元素
        const collectionName = document.createElement('h2');
        collectionName.textContent = item.name;

        // 创建藏品铸造者元素
        const collectionCaster = document.createElement('p');
        collectionCaster.textContent = '铸造者：' + item.caster;

        // 创建藏品日期元素
        const collectionDate = document.createElement('p');
        collectionDate.textContent = '日期：' + item.date;

        // 创建藏品价格元素
        const collectionPrice = document.createElement('p');
        collectionPrice.textContent = '价格：' + item.price;

        // 添加子元素
        collectionInfo.appendChild(collectionName);
        collectionInfo.appendChild(collectionCaster);
        collectionInfo.appendChild(collectionDate);
        collectionInfo.appendChild(collectionPrice);
        collectionItem.appendChild(collectionImage);
        collectionItem.appendChild(collectionInfo);
        collectionContainer.appendChild(collectionItem);
    });
}

// 初始化页面
renderCollection();