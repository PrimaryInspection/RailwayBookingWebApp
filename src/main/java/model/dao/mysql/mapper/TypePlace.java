package model.dao.mysql.mapper;

public enum TypePlace {
    B{
        @Override
        public String toString() {
            return "B";
        }
    },
    C{
        @Override
        public String toString() {
            return "C";
        }
    },
    L{
        @Override
        public String toString() {
            return "L";
        }
    }
}
