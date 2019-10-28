#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <set>

using namespace std;

struct Feladat{
    string question, category;
    int point, answer;
    
    Feladat(string& quest, int ans, int poi, string& cat) : question(quest), category(cat), point(poi), answer(ans){}
};

int main(){
    vector<Feladat> feladatok;
    ifstream input("felszam.txt");
    string currentLine, cat;
    int ev, point;
    
    while(getline(input, currentLine)){
        input >> ev >> point >> cat;
        feladatok.emplace_back(currentLine, ev, point, cat);
        getline(input, currentLine);
    }
    input.close();
    
    cout << "Feladatok sz�ma: " << feladatok.size() << "\n";
    
    //[0]: matek, [1]: 1 pont, [2]: 2 pont, [3]: 3 pont, [4]: min, [5]: max
    int counters[] {0, 0, 0, 0, feladatok[0].answer, feladatok[0].answer};
    set<string> categories;
    
    for(auto& feladat : feladatok){
        categories.insert(feladat.category);
        
        if(feladat.point < counters[4]){
            counters[4] = feladat.point;
        }
        if(feladat.point > counters[5]){
            counters[5] = feladat.point;
        }
        if(feladat.category == "matematika"){
            ++counters[0];
            if(feladat.point == 1){
                ++counters[1];
            }else if(feladat.point == 2){
                ++counters[2];
            }else{
                ++counters[3];
            }
        }
    }
    
    cout << "Matek feladatok sz�ma: " << counters[0] << ", 1 pontos feladatok: " << counters[1]
         << ", 2 pontos feladatok: " << counters[2] << ", 3 pontosak: " << counters[3] << "\n";
    
    cout << "Legkisebb feladat megold�s: " << counters[4] << ", legnagyobb: " << counters[5] << "\n";
    
    for(auto& print : categories){
        cout << print << ' ';
    }
    cout << "\n";
    
    string temakor;
    int valasz;
    cout << "�rj be 1 t�mak�rt!" << "\n";
    cin >> temakor;
    
    auto fel = feladatok[rand() % feladatok.size()];
    for(; fel.category != temakor; fel = feladatok[rand() % feladatok.size()]);
    
    cout << fel.question << "\n";
    cin >> valasz;
    
    if(valasz == fel.answer){
        cout << "�gyi vagy! Itt 1 keksz �s " << fel.answer << " pont!" << "\n"; 
    }else{
        cout << "Rossz v�lasz! 0 pont, helyes v�lasz: " << fel.answer << "\n";
    }
    
    set<int> generalt;
    for(int r = rand() % feladatok.size(); generalt.size() != 10; r = rand() % feladatok.size()){
        generalt.insert(r);
    }
    
    ofstream output("tesztfel.txt");
    for(int kek : generalt){
        auto& print = feladatok[kek];
        output << print.point << ' ' << print.answer << ' ' << print.question << "\n";
    }
    
    output.close();
    return 0;
}