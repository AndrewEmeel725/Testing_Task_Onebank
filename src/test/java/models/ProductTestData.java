package models;

public class ProductTestData {

        private int categoryIndex;
        private int variationIndex;
        private int quantity;
        private int productIndex;


        public ProductTestData(int categoryIndex,int productIndex, int variationIndex, int quantity) {
            this.categoryIndex = categoryIndex;
            this.variationIndex = variationIndex;
            this.productIndex= productIndex;
            this.quantity = quantity;
        }


        public int getCategoryIndex() { return categoryIndex; }
        public int getVariationIndex() { return variationIndex; }
        public int getProductIndex() { return productIndex; }
        public int getQuantity() { return quantity; }
}

