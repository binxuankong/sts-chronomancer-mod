package chronoMod.potions;

import basemod.abstracts.CustomPotion;
import chronoMod.ChronoMod;
import chronoMod.actions.ConsumeJadeAction;
import chronoMod.powers.JadePower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class JadePotion extends CustomPotion {
    public static final String POTION_ID = ChronoMod.makeID("JadePotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public static final Color LIQUID = CardHelper.getColor(204.0f, 0.0f, 0.0f);
    public static final Color HYBRID = CardHelper.getColor(204.0f, 0.0f, 0.0f);
    public static final Color SPOTS = CardHelper.getColor(255.0f, 153.0f, 153.0f);

    public JadePotion() {
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.T, PotionColor.NONE);
        this.potency = this.getPotency();
        this.description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        this.isThrown = false;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        for (int i = 0; i < this.potency; i++) {
            AbstractPower jade = p.getPower(JadePower.POWER_ID);
            if (jade != null) {
                this.addToBot(new LoseEnergyAction(jade.amount));
                this.addToBot(new ReducePowerAction(p, p, jade, 1));
                if (jade.amount == 0) {
                    this.addToBot(new RemoveSpecificPowerAction(p, p, jade));
                }
                this.addToBot(new ConsumeJadeAction());
            }
        }
    }

    @Override
    public int getPotency(final int potency) {
        return 2;
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new JadePotion();
    }

    public void upgradePotion()
    {
      this.potency += 1;
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
    }
}
