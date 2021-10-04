package chronoMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AstralBanishmentAction extends AbstractGameAction {
    private DamageInfo info;
    private int energyGainAmt;

    public AstralBanishmentAction(DamageInfo info, int energyAmt) {
        this.info = info;
        this.energyGainAmt = energyAmt;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.FIRE;
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true,
                AbstractDungeon.cardRandomRng);

        if (this.target != null) {
            this.setValues(target, info);
            this.addToBot(new DamageAction(this.target, this.info, this.attackEffect));
            if (this.target.isDying || this.target.currentHealth <= 0) {
                this.addToBot(new GainEnergyAction(this.energyGainAmt));
            }
        }

        this.isDone = true;
    }
}
