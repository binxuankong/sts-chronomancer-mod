package chronoMod.actions;

import chronoMod.ChronoMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

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
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.FIRE));
            this.setValues(target, info);
            this.target.damage(this.info);
            if (this.target.isDying || this.target.currentHealth <= 0) {
                ChronoMod.logger.info(this.target.name, this.target.currentHealth);
                this.addToBot(new GainEnergyAction(this.energyGainAmt));
            }
        }

        this.isDone = true;
    }
}
