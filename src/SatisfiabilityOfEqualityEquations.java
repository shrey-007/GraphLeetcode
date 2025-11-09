import java.util.ArrayList;
import java.util.List;

/**
 * You are given an array of strings equations that represent relationships between variables where each string
 * equations[i] is of length 4 and takes one of two different forms: "xi==yi" or "xi!=yi".Here, xi and yi are lowercase
 * letters (not necessarily different) that represent one-letter variable names.
 *
 * Return true if it is possible to assign integers to variable names so as to satisfy all the given equations, or false
 * otherwise.
 * */
public class SatisfiabilityOfEqualityEquations {
    // suppose tumhe ye diya hai question a=b, b=c, c!=a toh kese ise kroge. You can do it using a hashmap.
    // pehle 2 equations keh rhi hai ki a,b,c teeno hashmap mai aaege and 3rd equation keh rhi hai ki c and a ek saath
    // hashmap mai nhi aaege toh ye contradiction hai toh return false
    // is approach mai problem ye hai ki suppose ye question hai={m=n, n=p, p=m ,a=b, b=c, c!=a} toh isme m,n,p ek
    // hashmap mai hoge and a,b,c alag hashmap mai hoge means jitne component utne hashmap toh pata kese padega kis
    // character ko kis hashmap mai daalna hai

    // Toh ise hashmap se ni kr skte. Let's se whether ise adjacency list se kr skte hai ki nhi. {a=b, b=c, c!=a}
    // a->{b}, b->{c,a}, c->{b}. According to first 2 equations a,b ke beech and b,c ke beech ek undirected edge hai.
    // according to 3rd equation c,a ke beech koi edge nhi hai, and it is true sahi mai koi edge nhi hai toh ye
    // contradict hua hi nhi. contradict hota toh false return krte but ye true return kr dega

    // we have to do it using disjoint set
    // pehele only voh equaltions ko dekho jinme == hai and unko same set mai daalo
    // phir != vali equaltions ko dekho, agar voh already same set mai and phir bhi != hai means ki false return krdo
    public boolean equationsPossible(String[] equations) {
        int n = equations.length;
        Disjoint disjoint = new Disjoint(26);

        // first process only == equations
        for (int i = 0; i < n; i++) {
            String equation = equations[i];
            char char1 = equation.charAt(0);
            int u = char1-'a';
            char char2 = equation.charAt(3);
            int v = char2-'a';
            char sign = equation.charAt(1);

            if(sign=='='){
                // if char1==char2 means dono same set se hai
                disjoint.unionByRank(u,v);
            }
        }

        // yaha aane tak disjoint set ban gaya, jo variable equal the voh same set mai hai
        // now process != equaltions

        for (int i = 0; i < n; i++) {
            String equation = equations[i];
            char char1 = equation.charAt(0);
            int u = char1-'a';
            char char2 = equation.charAt(3);
            int v = char2-'a';
            char sign = equation.charAt(1);

            if(sign=='!'){
                // if u!=v diya hai and u and v pehle se hi same set mai hai means ki koi == equation ne unhe same set mai
                // daal toh return false
                if(disjoint.findUltimateParent(u)==disjoint.findUltimateParent(v)){return false;}
            }
        }

        return true;
    }

    class Disjoint{
        int v;
        List<Integer> parent;
        List<Integer> rank;

        Disjoint(int v){
            this.v=v;
            parent = new ArrayList<>();
            rank = new ArrayList<>();
            for(int i=0;i<=v;i++){
                parent.add(i);
                rank.add(0);
            }
        }

        public int findUltimateParent(int u){
            if(parent.get(u)==u){return u;}

            int tempParent = parent.get(u);
            int tempParent2 = findUltimateParent(tempParent);
            parent.set(u,tempParent2);

            return tempParent2;
        }

        public void unionByRank(int u,int v){
            int ultimateU = findUltimateParent(u);
            int ultimateV = findUltimateParent(v);

            if (ultimateU == ultimateV) return;

            if (rank.get(ultimateU) < rank.get(ultimateV)) {
                parent.set(ultimateU, ultimateV);
            } else if (rank.get(ultimateU) > rank.get(ultimateV)) {
                parent.set(ultimateV, ultimateU);
            } else {
                parent.set(ultimateU, ultimateV);
                rank.set(ultimateV, rank.get(ultimateV) + 1);
            }

        }
    }
}
