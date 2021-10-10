package chronoMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EssenceFluxAction extends AbstractGameAction {
    private DamageInfo info;

    public EssenceFluxAction(DamageInfo info) {
        this.info = info;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true,
                AbstractDungeon.cardRandomRng);

        if (this.target != null) {
            this.addToTop(new DamageAction(this.target, this.info, AttackEffect.BLUNT_LIGHT));
        }

        this.isDone = true;
    }
}
